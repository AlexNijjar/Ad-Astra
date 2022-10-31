package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import earth.terrarium.ad_astra.screen.NoInventorySlot;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LargeVehicleScreenHandler extends AbstractVehicleScreenHandler {

    public LargeVehicleScreenHandler(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (VehicleEntity) inventory.player.level.getEntity(buf.readInt()));
    }

    public LargeVehicleScreenHandler(int syncId, Inventory inventory, VehicleEntity entity) {
        super(ModScreenHandlers.LARGE_VEHICLE_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[]{

                // Left input slot.
                new NoInventorySlot(entity.getInventory(), 0, 20, 26) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        if (!super.mayPlace(stack)) {
                            return false;
                        }
                        return FluidHooks.isFluidContainingItem(stack);
                    }
                },
                // Left output slot.
                new NoInventorySlot(entity.getInventory(), 1, 20, 56) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                },

                // Inventory
                new NoInventorySlot(entity.getInventory(), 2, 86, 16),
                //
                new NoInventorySlot(entity.getInventory(), 3, 86 + 18, 16),
                //
                new NoInventorySlot(entity.getInventory(), 4, 86 + 18 * 2, 16),
                //
                new NoInventorySlot(entity.getInventory(), 5, 86 + 18 * 3, 16),
                //
                new NoInventorySlot(entity.getInventory(), 6, 86, 34),
                //
                new NoInventorySlot(entity.getInventory(), 7, 86 + 18, 34),
                //
                new NoInventorySlot(entity.getInventory(), 8, 86 + 18 * 2, 34),
                //
                new NoInventorySlot(entity.getInventory(), 9, 86 + 18 * 3, 34),
                //
                new NoInventorySlot(entity.getInventory(), 10, 86, 52),
                //
                new NoInventorySlot(entity.getInventory(), 11, 86 + 18, 52),
                //
                new NoInventorySlot(entity.getInventory(), 12, 86 + 18 * 2, 52),
                //
                new NoInventorySlot(entity.getInventory(), 13, 86 + 18 * 3, 52),
                //
                new NoInventorySlot(entity.getInventory(), 14, 86, 70),
                //
                new NoInventorySlot(entity.getInventory(), 15, 86 + 18, 70),
                //
                new NoInventorySlot(entity.getInventory(), 16, 86 + 18 * 2, 70),
                //
                new NoInventorySlot(entity.getInventory(), 17, 86 + 18 * 3, 70),});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 15;
    }
}