package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractMachineScreenHandler<T extends AbstractMachineBlockEntity> extends AbstractContainerMenu {

    protected final T machine;
    protected final Level level;
    protected final Player player;
    protected long energyAmount;
    protected List<FluidHolder> fluids;

    public AbstractMachineScreenHandler(MenuType<?> type, int syncId, Inventory inventory, T entity) {
        this(type, syncId, inventory, entity, new Slot[]{});
    }

    // Add additional slots.
    public AbstractMachineScreenHandler(MenuType<?> type, int syncId, Inventory inventory, T entity, Slot[] slots) {
        super(type, syncId);
        this.machine = entity;
        this.level = entity.getLevel();
        this.player = inventory.player;

        checkContainerSize(inventory, this.machine.getInventorySize());

        this.machine.startOpen(inventory.player);

        for (Slot slot : slots) {
            this.addSlot(slot);
        }

        this.setPlayerInventory(inventory);
    }

    public T getMachine() {
        return this.machine;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.machine.stillValid(player);
    }

    protected void setPlayerInventory(Inventory inventory) {
        int m;
        int l;

        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + this.getPlayerInventoryOffset() + m * 18));
            }
        }

        for (m = 0; m < 9; ++m) {
            addSlot(new Slot(inventory, m, 8 + m * 18, 142 + this.getPlayerInventoryOffset()));
        }
    }

    public int getPlayerInventoryOffset() {
        return 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (index < this.machine.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.machine.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.machine.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }

    public long getEnergyAmount() {
        return energyAmount;
    }

    public void setEnergyAmount(long energyAmount) {
        this.energyAmount = energyAmount;
    }

    public List<FluidHolder> getFluids() {
        return fluids == null ? List.of(FluidHooks.emptyFluid(), FluidHooks.emptyFluid()) : fluids;
    }

    public void setFluids(List<FluidHolder> fluids) {
        this.fluids = fluids;
    }

    // Fixes a client sync issue.
    @Override
    public void clicked(int slotIndex, int button, ClickType actionType, Player player) {
        super.clicked(slotIndex, button, actionType, player);
        this.broadcastFullState();
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        syncClientScreen();
    }

    public abstract void syncClientScreen();
}