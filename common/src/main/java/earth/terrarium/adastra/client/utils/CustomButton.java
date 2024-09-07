package earth.terrarium.adastra.client.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.client.screens.PlanetsScreen;
import earth.terrarium.adastra.common.utils.ColourUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.LinkedList;
import java.util.List;

public class CustomButton extends Button {

    public static final ResourceLocation LARGE_BUTTON_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/buttons/large_button.png");
    public static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/buttons/button.png");
    public static final ResourceLocation SMALL_BUTTON_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/buttons/small_button.png");
    public static final ResourceLocation STEEL_BUTTON_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/buttons/steel_button.png");

    private final int startY;
    private final Component label;
    private final ButtonType buttonSize;
    private final Color buttonColourLightened;
    private final Color buttonColour;
    private final Planet planetInfo;

    private final PlanetsScreen.TooltipType tooltip;
    public boolean doScissor = true;

    public CustomButton(int x, int y, Component label, ButtonType size, ButtonColor buttonColor, PlanetsScreen.TooltipType tooltip, Planet planetInfo, OnPress onPress) {

        super(x, y, size.getWidth(), size.getHeight(), adjustText(label), onPress, Button.DEFAULT_NARRATION);

        this.startY = y;
        this.label = label;
        this.buttonSize = size;
        Color colour = buttonColor.getColour();
        // The button becomes lightened when the mouse hovers over the button.
        this.buttonColourLightened = ColourUtils.lighten(colour, 0.1f);
        // This is the normal colour when the button is not being hovered over.
        this.buttonColour = colour;
        this.tooltip = tooltip;
        this.planetInfo = planetInfo;
    }

    // Cut off the text if it's too large.
    public static Component adjustText(Component label) {
        int length = label.getString().length();
        if (length > 12 && length != 13) {
            return Component.nullToEmpty(label.getString(12) + ".");
        } else {
            return label;
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        this.setY(startY);
    }

    //    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.visible) {
            Minecraft minecraft = Minecraft.getInstance();
            double scale = minecraft.getWindow().getGuiScale();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int scissorY = (int) (((screenHeight / 2) - 83) * scale);

            boolean over = this.isMouseOver(mouseX, mouseY);
            Color color = over ? this.buttonColourLightened : this.buttonColour;

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(5, 0, 0);
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.enableDepthTest();

            if (this.doScissor) {
                RenderSystem.enableScissor(0, scissorY, (int) (215 * scale), (int) (127 * scale));
            }

            RenderSystem.setShaderColor(color.getFloatRed(), color.getFloatGreen(), color.getFloatBlue(), this.buttonColour.getFloatAlpha());
            RenderSystem.setShaderTexture(0, switch (this.buttonSize) {
                case LARGE -> LARGE_BUTTON_TEXTURE;
                case NORMAL -> BUTTON_TEXTURE;
                case SMALL -> SMALL_BUTTON_TEXTURE;
                case STEEL -> STEEL_BUTTON_TEXTURE;
            });

            guiGraphics.blit(WIDGETS_LOCATION, (this.buttonSize.equals(ButtonType.LARGE) ? this.getX() - 2 : this.getX()), this.getY(), 0, 0, this.width, this.height, buttonSize.getWidth(), buttonSize.getHeight());
            drawText(guiGraphics, minecraft);

            if (this.doScissor) {
                RenderSystem.disableScissor();
            }

            if (this.isMouseOver(mouseX, mouseY)) {
                renderTooltips(guiGraphics, mouseX, mouseY, minecraft);
            }

            guiGraphics.pose().popPose();
            RenderSystem.disableDepthTest();
        }
    }

    public void drawText(GuiGraphics graphics, Minecraft minecraft) {
        Font textRenderer = minecraft.font;
        int colour = this.active ? 16777215 : 10526880;
        graphics.pose().pushPose();
        float scale = 0.9f;
        graphics.pose().scale(scale, scale, scale);
        int x = (this.buttonSize.equals(ButtonType.LARGE) ? this.getX() - 2 : this.getX());
        graphics.pose().translate(4 + (x / 9.5f), this.getY() / 8.5f, 0);
        graphics.drawCenteredString(textRenderer, this.getMessage(), x + this.width / 2, this.getY() + (this.height - 8) / 2, colour | Mth.ceil(this.alpha * 255.0f) << 24);

        graphics.pose().popPose();;
    }

    public int getStartY() {
        return this.startY;
    }

    private void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, Minecraft minecraft) {

        Screen screen = minecraft.screen;
        List<Component> textEntries = new LinkedList<>();

        switch (tooltip) {
            case NONE -> {

            }
            case GALAXY -> {
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("category").getString() + ": §b" + label.getString()));
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("type").getString() + ": §5" + ScreenUtils.createText("galaxy").getString()));
            }
            case SOLAR_SYSTEM -> {
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("category").getString() + ": §b" + label.getString()));
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("type").getString() + ": §3" + ScreenUtils.createText("solar_system").getString()));
            }
            case CATEGORY -> {
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("category").getString() + ": §a" + label.getString()));
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("provided").getString() + ": §b" + Component.translatable("item.ad_astra.tier_" + planetInfo.tier() + "_rocket").getString()));
            }
            case PLANET -> {
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("type").getString() + ": §3" + (planetInfo.parent_world() == null ? ScreenUtils.createText("planet").getString() : ScreenUtils.createText("moon").getString())));
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("gravity").getString() + ": §3" + planetInfo.gravity() + " m/s"));
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("oxygen").getString() + ": §" + (planetInfo.oxygen() ? ('a' + ScreenUtils.createText("oxygen.true").getString()) : ('c' + ScreenUtils.createText("oxygen.false").getString()))));
                String temperatureColour = "§a";

                // Make the temperature text look orange when the temperature is hot and blue when the temperature is cold.
                if (planetInfo.temperature() > 50) {
                    // Hot.
                    temperatureColour = "§6";
                } else if (planetInfo.temperature() < -20) {
                    // Cold.
                    temperatureColour = "§1";
                }

                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("temperature").getString() + ": " + temperatureColour + " " + planetInfo.temperature() + " °C"));
            }
            case SPACE_STATION -> {
                PlanetsScreen currentScreen = (PlanetsScreen) minecraft.screen;
                textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("item_requirement").getString()));

                currentScreen.ingredients.forEach(ingredient -> {
                    boolean isEnough = ingredient.getFirst().getCount() >= ingredient.getSecond();
                    textEntries.add(Component.nullToEmpty("§" + (isEnough ? "a" : "c") + ingredient.getFirst().getCount() + "/" + ingredient.getSecond() + " §3" + ingredient.getFirst().getHoverName().getString()));
                });
                textEntries.add(Component.nullToEmpty("§c----------------"));
            }
            default -> {

            }
        }

        if (tooltip.equals(PlanetsScreen.TooltipType.ORBIT) || tooltip.equals(PlanetsScreen.TooltipType.SPACE_STATION)) {
            textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("type").getString() + ": §3" + ScreenUtils.createText("orbit").getString()));
            textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("gravity").getString() + ": §3" + ScreenUtils.createText("no_gravity").getString()));
            textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("oxygen").getString() + ": §c " + ScreenUtils.createText("oxygen.false").getString()));
            textEntries.add(Component.nullToEmpty("§9" + ScreenUtils.createText("temperature").getString() + ": §1 " + -270.0f + " °C"));
        }

        guiGraphics.renderComponentTooltip(minecraft.font, textEntries, mouseX, mouseY);


    }


}