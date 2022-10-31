package earth.terrarium.ad_astra.client.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.blocks.machines.entity.CompressorBlockEntity;
import earth.terrarium.ad_astra.screen.handler.CompressorScreenHandler;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import java.awt.*;

@Environment(EnvType.CLIENT)
public class CompressorScreen extends AbstractMachineScreen<CompressorBlockEntity, CompressorScreenHandler> {

    public static final int ENERGY_LEFT = 147;
    public static final int ENERGY_TOP = 30;
    public static final int HAMMER_LEFT = 67;
    public static final int HAMMER_TOP = 63;
    private static final ResourceLocation TEXTURE = new ModResourceLocation("textures/gui/screens/compressor.png");

    public CompressorScreen(CompressorScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.imageWidth = 177;
        this.imageHeight = 196;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        super.renderBg(matrices, delta, mouseX, mouseY);


        GuiUtil.drawHammer(matrices, this.leftPos + HAMMER_LEFT, this.topPos + HAMMER_TOP, this.machine.getCookTime(), this.machine.getCookTimeTotal());
        GuiUtil.drawEnergy(matrices, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, this.menu.getEnergyAmount(), this.machine.getMaxCapacity());
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            GuiUtil.drawEnergyTooltip(this, matrices, this.menu.getEnergyAmount(), this.machine.getMaxCapacity(), mouseX, mouseY);
        }

        // Burn time tooltip.
        if (GuiUtil.isHovering(this.getHammerBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Component.translatable("gauge.ad_astra.cook_time", this.machine.getCookTime(), this.machine.getCookTimeTotal()), mouseX, mouseY);
        }
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
    }

    public Rectangle getHammerBounds() {
        return GuiUtil.getHammerBounds(this.leftPos + HAMMER_LEFT, this.topPos + HAMMER_TOP);
    }
}