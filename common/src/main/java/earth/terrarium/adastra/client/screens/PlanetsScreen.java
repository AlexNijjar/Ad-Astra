package earth.terrarium.adastra.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Pair;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.client.events.AdAstraClientEvents;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.api.systems.PlanetData;
import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.client.components.LabeledImageButton;
import earth.terrarium.adastra.client.resourcepack.PlanetRing;
import earth.terrarium.adastra.client.resourcepack.SolarSystem;
import earth.terrarium.adastra.client.utils.Category;
import earth.terrarium.adastra.client.utils.ScreenUtils;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundLandOnSpaceStationPacket;
import earth.terrarium.adastra.common.network.messages.ServerboundLandPacket;
import earth.terrarium.adastra.common.planets.AdAstraData;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PlanetsScreen extends AbstractContainerScreen<PlanetsMenu> {
    public static final ResourceLocation BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/button.png");
    public static final ResourceLocation BACK_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/back_button.png");
    public static final ResourceLocation PLUS_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/plus_button.png");
    public static final ResourceLocation SELECTION_MENU = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/selection_menu.png");
    public static final ResourceLocation SMALL_SELECTION_MENU = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/small_selection_menu.png");

    private final List<Button> buttons = new ArrayList<>();
    private Button backButton;
    private double scrollAmount;

    private final List<Button> spaceStationButtons = new ArrayList<>();
    private Button addSpaceStatonButton;
    private double spaceStationScrollAmount;

    public final List<Pair<ItemStack, Integer>> ingredients = new ArrayList<>();

    private final boolean hasMultipleSolarSystems;
    private final boolean hasMultipleGalaxies;

    private int pageIndex;

    private float guiTime;

    @Nullable
    private ResourceLocation selectedGalaxy = new ResourceLocation(AdAstra.MOD_ID, "galaxy");

    @Nullable
    private ResourceLocation selectedSolarSystem = new ResourceLocation(AdAstra.MOD_ID, "solar_system");

    @Nullable
    private Planet selectedPlanet;

    @Nullable
    private Planet newPlanet;

    @Nullable
    private Planet parentPlanet;


    private Category currentCategory = new Category(new ResourceLocation(AdAstra.MOD_ID, "galaxy"), null);

    final Set<Category> solarSystemsCategories = new HashSet<>();
    final Set<Category> galaxyCategories = new HashSet<>();

    List<ResourceLocation> solarSystemList = new ArrayList<>();

    final Set<List<ResourceLocation>> newSolarSystemsCategories = new HashSet<List<ResourceLocation>>();

    public PlanetsScreen(PlanetsMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.imageWidth = width;
        this.imageHeight = height;

        var planets = AdAstraData.planets().values().stream()
            .filter(planet -> !menu.disabledPlanets().contains(planet.dimension().location()))
            .filter(planet -> menu.tier() >= planet.tier()).toList();

        hasMultipleGalaxies = planets.stream().map(Planet::galaxy).distinct().count() > 1;

        hasMultipleSolarSystems = planets.stream().map(Planet::solarSystem).distinct().count() > 1;

        if(hasMultipleGalaxies){
            pageIndex =  0;
        }
        else{
            pageIndex =  1;
        }

        if (AdAstraClient.galaxies.size() <= 1 && !hasMultipleGalaxies) {
            currentCategory = new Category(new ResourceLocation(AdAstra.MOD_ID, "milky_way"), new Category(new ResourceLocation(AdAstra.MOD_ID, "galaxy"), null));
        }

        // Set the initial gui time to the level time. This creates a random start position for each rotating object.
        guiTime = menu.player().level().getRandom().nextFloat() * 100000.0f;
    }

    @Override
    protected void init() {
        buttons.clear();
        spaceStationButtons.clear();
        spaceStationScrollAmount = 0;

        switch (pageIndex) {
            case 0 -> {
                selectedPlanet = null;
                selectedSolarSystem = null;
                createSolarSystemButtons();
                createGalaxyButtons();
            }
            case 1 -> {
                selectedPlanet = null;
                createSolarSystemButtons();
                selectedPlanet = null;
            }
            case 2 -> {
                selectedPlanet = null;
                createPlanetButtons();
            }
            case 3 -> {
                createPlanetButtons();
                if(selectedPlanet != null){
                    createSelectedPlanetButtons();
                }
            }
            case 4 -> {
                if(selectedPlanet != null){
                    createSelectedPlanetButtons();
                }
            }
        }


        List<Planet> planets = new ArrayList<>(PlanetData.planets());
        planets.sort(Comparator.comparing(g -> g.translation().substring(Math.abs(g.translation().indexOf(".text")))));
        planets.forEach(planet -> {
            if (menu.tier() >= planet.tier()) {
                Category galaxyCategory = new Category(planet.galaxy(), new Category(new ResourceLocation(AdAstra.MOD_ID, "galaxy"), null));
                Category solarSystemCategory = new Category(planet.solarSystem(), galaxyCategory);
                this.galaxyCategories.add(galaxyCategory);
                this.solarSystemsCategories.add(solarSystemCategory);
                List<String> disabledPlanets = List.of(AdAstraConfig.disabledPlanets.split(","));
            }
        });

        addSpaceStatonButton = addRenderableWidget(new LabeledImageButton(114, height / 2 - 41, 12, 12, 0, 12, 12, PLUS_BUTTON, 12, 24, b -> {
            if (selectedPlanet != null && pageIndex > 2 ) {
                int ownedSpaceStationCount = menu.getOwnedAndTeamSpaceStations(selectedPlanet.orbitIfPresent()).size();
                Component name = Component.translatable("text.ad_astra.text.space_station_name", ownedSpaceStationCount + 1);
                menu.constructSpaceStation(selectedPlanet.dimension(), name);
                close();
            }
        }));
        if (selectedPlanet != null && pageIndex > 2) {
            addSpaceStatonButton.setTooltip(getSpaceStationRecipeTooltip(selectedPlanet.orbitIfPresent()));
            addSpaceStatonButton.active = selectedPlanet != null && menu.canConstruct(selectedPlanet.orbitIfPresent()) && !menu.isInSpaceStation(selectedPlanet.orbitIfPresent());
        }

        backButton = addRenderableWidget(new LabeledImageButton(10, height / 2 - 85, 12, 12, 0, 12, 12, BACK_BUTTON, 12, 24, b -> {
            if (pageIndex != 3) this.scrollAmount = 0;
            if (pageIndex == 4){
                for (var planet : menu.getSortedPlanets()) {
                    if (planet.isSpace()) continue;
                    if (menu.tier() < planet.tier()) continue;
                    if (!planet.solarSystem().equals(selectedSolarSystem)) continue;
                    if (!planet.parent_world().isEmpty()) continue;
                    if(!selectedPlanet.parent_world().isEmpty() && selectedPlanet.parent_world().get().location().toString().equals("ad_astra:earth") && planet.dimension().location().toString().equals("minecraft:overworld")){
                        selectedPlanet = planet;
                        break;
                    } else {
                        if(!selectedPlanet.parent_world().isEmpty() && planet.dimension().location().toString().equals(selectedPlanet.parent_world().get().location().toString())) {
                            selectedPlanet = planet;
                            break;
                        }
                    }
                }
            }
            pageIndex--;
            rebuildWidgets();
        }));

        if(hasMultipleGalaxies && pageIndex > 0){
            backButton.visible = true;
        }
        else{
            if(hasMultipleSolarSystems && pageIndex > 1) {
                backButton.visible = true;
            } else {
                backButton.visible = false;
            }

        }
        addSpaceStatonButton.visible = pageIndex > 2 && selectedPlanet != null;

    }

    private void createGalaxyButtons() {
        buttons.clear();
        selectedGalaxy = null;
        selectedSolarSystem = null;
        List<ResourceLocation> solarSystemList = new ArrayList<>();
        List<ResourceLocation> galaxies = new ArrayList<>(AdAstraData.galaxies());
        galaxies.sort(Comparator.comparing(ResourceLocation::getPath));
        galaxies.forEach(galaxy -> {
            var button = addWidget(new LabeledImageButton(10, 0, 99, 20, 0, 0, 20, BUTTON, 99, 40, b -> {
                pageIndex = 1;
                selectedGalaxy = galaxy;
                rebuildWidgets();
            }, Component.translatableWithFallback("galaxy.%s.%s".formatted(galaxy.getNamespace(), galaxy.getPath()), title(galaxy.getPath()))));
            buttons.add(button);
        });
    }

    private void createSolarSystemButtons() {
        buttons.clear();
        selectedSolarSystem = null;
        List<ResourceLocation> solarSystems = new ArrayList<>(AdAstraData.solarSystems());
        List<ResourceLocation> solarSystemList = new ArrayList<>();
        solarSystemsCategories.forEach(solarSystem -> {
            if(solarSystem.parent().id().equals(selectedGalaxy)){
                solarSystemList.add(solarSystem.id());
            }
        });
        solarSystemList.sort(Comparator.naturalOrder());
        solarSystems.sort(Comparator.comparing(ResourceLocation::getPath));
        solarSystemList.forEach(solarSystem -> {
            var button = addWidget(new LabeledImageButton(10, 0, 99, 20, 0, 0, 20, BUTTON, 99, 40, b -> {
                pageIndex = 2;
                selectedSolarSystem = solarSystem;
                rebuildWidgets();
            }, Component.translatableWithFallback("solar_system.%s.%s".formatted(solarSystem.getNamespace(), solarSystem.getPath()), title(solarSystem.getPath()))));
            buttons.add(button);
        });
    }

    private void createPlanetButtons() {
        buttons.clear();
        for (var planet : menu.getSortedPlanets()) {
            if (planet.isSpace()) continue;
            if (menu.tier() < planet.tier()) continue;
            if (!planet.solarSystem().equals(selectedSolarSystem)) continue;
            if (!planet.parent_world().isEmpty()) continue;
            buttons.add(addWidget(new LabeledImageButton(10, 0, 99, 20, 0, 0, 20, BUTTON, 99, 40, b -> {
                pageIndex = 3;
                selectedPlanet = planet;
                rebuildWidgets();
            }, menu.getPlanetName(planet.dimension()))));
        }
    }

    private void createSelectedPlanetButtons() {
        if (selectedPlanet == null) return;
        buttons.clear();
        BlockPos pos = menu.getLandingPos(selectedPlanet.dimension(), true);
        var button = addRenderableWidget(new LabeledImageButton(
            114, height / 2 - 77, 99, 20, 0, 0, 20, BUTTON,
            99, 40, b -> land(selectedPlanet.dimension()), Component.translatable("text.ad_astra.text.land")));
        button.setTooltip(Tooltip.create(Component.translatable("tooltip.ad_astra.land",
            menu.getPlanetName(selectedPlanet.dimension()), pos.getX(), pos.getZ()).withStyle(ChatFormatting.AQUA)));

        for (var planet : menu.getSortedPlanets()) {
            if (planet.isSpace()) continue;
            if (menu.tier() < planet.tier()) continue;
            if (!planet.solarSystem().equals(selectedSolarSystem)) continue;
            if (planet.parent_world().isEmpty()) continue;
            if(selectedPlanet.dimension().location().toString().equals("minecraft:overworld")){
                if (!planet.parent_world().isEmpty() && !planet.parent_world().get().location().toString().equals("ad_astra:earth")) {
                        continue;
                }
            } else {
                if (!planet.parent_world().isEmpty() && !planet.parent_world().get().location().equals(selectedPlanet.dimension().location())) {
                    continue;
                }
            }
            buttons.add(addWidget(new LabeledImageButton(10, 0, 99, 20, 0, 0, 20, BUTTON, 99, 40, b -> {
                selectedPlanet = planet;
                pageIndex = 4;
                rebuildWidgets();
            }, menu.getPlanetName(planet.dimension()))));
        }
        addSpaceStationButtons(selectedPlanet.orbitIfPresent());
    }

    private void addSpaceStationButtons(ResourceKey<Level> dimension) {
        menu.getOwnedAndTeamSpaceStations(dimension).forEach(station -> {
            ChunkPos pos = station.getSecond().position();
            var button = addWidget(new LabeledImageButton(114, height / 2, 99, 20, 0, 0, 20, BUTTON, 99, 40, b ->
                landOnSpaceStation(dimension, pos), station.getSecond().name()));
            button.setTooltip(getSpaceStationLandTooltip(dimension, pos, station.getFirst()));
            spaceStationButtons.add(button);
        });
    }

    public Tooltip getSpaceStationLandTooltip(ResourceKey<Level> dimension, ChunkPos pos, String owner) {
        return Tooltip.create(CommonComponents.joinLines(
            Component.translatable("tooltip.ad_astra.space_station_land", menu.getPlanetName(dimension), pos.getMiddleBlockX(), pos.getMiddleBlockZ()).withStyle(ChatFormatting.AQUA),
            Component.translatable("tooltip.ad_astra.space_station_owner", owner).withStyle(ChatFormatting.GOLD)
        ));
    }

    public Tooltip getSpaceStationRecipeTooltip(ResourceKey<Level> planet) {
        List<Component> tooltip = new ArrayList<>();
        BlockPos pos = menu.getLandingPos(planet, false);
        tooltip.add(Component.translatable("tooltip.ad_astra.construct_space_station_at", menu.getPlanetName(planet), pos.getX(), pos.getZ()).withStyle(ChatFormatting.AQUA));

        if (menu.isInSpaceStation(planet) || menu.isClaimed(planet)) {
            tooltip.add(Component.translatable("text.ad_astra.space_station.already_exists").withStyle(ChatFormatting.RED));
            return Tooltip.create(CommonComponents.joinLines(tooltip));
        } else {
            tooltip.add(Component.translatable("tooltip.ad_astra.construction_cost").copy().withStyle(ChatFormatting.AQUA));
        }

        List<Pair<ItemStack, Integer>> ingredients = menu.ingredients().get(planet);
        if (ingredients == null) return Tooltip.create(CommonComponents.joinLines(tooltip));
        for (var ingredient : ingredients) {
            var stack = ingredient.getFirst();
            int amountOwned = ingredient.getSecond();
            boolean hasEnough = menu.player().isCreative() || menu.player().isSpectator() || amountOwned >= stack.getCount();
            tooltip.add(Component.translatable("tooltip.ad_astra.requirement", amountOwned, stack.getCount(), stack.getHoverName()
                    .copy().withStyle(ChatFormatting.DARK_AQUA))
                .copy().withStyle(hasEnough ? ChatFormatting.GREEN : ChatFormatting.RED));
        }

        return Tooltip.create(CommonComponents.joinLines(tooltip));
    }

    public void land(ResourceKey<Level> dimension) {
        NetworkHandler.CHANNEL.sendToServer(new ServerboundLandPacket(dimension, true));
        close();
    }

    public void landOnSpaceStation(ResourceKey<Level> dimension, ChunkPos pos) {
        NetworkHandler.CHANNEL.sendToServer(new ServerboundLandOnSpaceStationPacket(dimension, pos));
        close();
    }

   private void renderButtons(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int scrollPixels = (int) scrollAmount;
        try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), 0, height / 2 - 43, 112, 131)) {
            for (var button : buttons) {
                button.render(graphics, mouseX, mouseY, partialTick);
            }
            for (int i = 0; i < buttons.size(); i++) {
                var button = buttons.get(i);
                button.setY((i * 24 - scrollPixels) + (height / 2 - 41));
            }
        }
        if (pageIndex > 2 && selectedPlanet != null) {
            int spaceStationScrollPixels = (int) spaceStationScrollAmount;

            try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), 112, height / 2 - 2, 112, 90)) {
                for (var button : spaceStationButtons) {
                    button.render(graphics, mouseX, mouseY, partialTick);
                }
                for (int i = 0; i < spaceStationButtons.size(); i++) {
                    var button = spaceStationButtons.get(i);
                    button.setY((i * 24 - spaceStationScrollPixels) + (height / 2));
                }
            }
        }
    }

    // StringUtils only replaces the first word so WordUtils is needed
    public static String title(String string) {
        return WordUtils.capitalizeFully(string.replace("_", " "));
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {}

    protected void close() {
        if(hasMultipleGalaxies){
            pageIndex = 0;
        } else {
            pageIndex = 1;
        }
        onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (mouseX < 112 && mouseX > 6 && mouseY > height / 2f - 43 && mouseY < height / 2f + 88) {
            setScrollAmount(scrollAmount - delta * 16 / 2f);
        } else if (mouseX > 112 && mouseX < 224 && mouseY > height / 2f - 2 && mouseY < height / 2f + 88) {
            setSpaceStationScrollAmount(spaceStationScrollAmount - delta * 16 / 2f);
        }
        return true;
    }

    protected void setScrollAmount(double amount) {
        scrollAmount = Mth.clamp(amount, 0.0, Math.max(0, buttons.size() * 24 - 131));
    }

    protected void setSpaceStationScrollAmount(double amount) {
        spaceStationScrollAmount = Mth.clamp(amount, 0.0, Math.max(0, spaceStationButtons.size() * 24 - 90));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        if (pageIndex >= 2  && selectedPlanet !=null) {
            guiGraphics.blit(SELECTION_MENU, 7, height / 2 - 88, 0, 0, 209, 177, 209, 177);
            guiGraphics.drawCenteredString(font, Component.translatable("text.ad_astra.text.space_station"), 163, height / 2 - 15, 0xffffff);
        } else {
            guiGraphics.blit(SMALL_SELECTION_MENU, 7, height / 2 - 88, 0, 0, 105, 177, 105, 177);
        }

        if (pageIndex >= 2){
            if(selectedPlanet != null) {
                guiGraphics.drawCenteredString(font, Component.translatableWithFallback("planet.%s.%s".formatted(selectedPlanet.dimension().location().getNamespace(), selectedPlanet.dimension().location().getPath()), title(selectedPlanet.dimension().location().getPath())), 57, height / 2 - 60, 0xffffff);
            } else {
                if(selectedSolarSystem != null) {
                    guiGraphics.drawCenteredString(font, Component.translatableWithFallback("solar_system.%s.%s".formatted(selectedSolarSystem.getNamespace(), selectedSolarSystem.getPath()), title(selectedSolarSystem.getPath())), 57, height / 2 - 60, 0xffffff);
                }
            }
        } else if (pageIndex == 1) {
            if(selectedSolarSystem != null){
                guiGraphics.drawCenteredString(font, Component.translatableWithFallback("solar_system.%s.%s".formatted(selectedSolarSystem.getNamespace(), selectedSolarSystem.getPath()), title(selectedSolarSystem.getPath())), 57, height / 2 - 60, 0xffffff);
            } else {
                guiGraphics.drawCenteredString(font, Component.translatableWithFallback("galaxy.%s.%s".formatted(selectedGalaxy.getNamespace(), selectedGalaxy.getPath()), title(selectedGalaxy.getPath())), 57, height / 2 - 60, 0xffffff);
            }
        } else if (pageIndex == 0 && selectedGalaxy != null) {
            guiGraphics.drawCenteredString(font, Component.translatableWithFallback("galaxy.%s.%s".formatted(selectedGalaxy.getNamespace(), selectedGalaxy.getPath()), title(selectedGalaxy.getPath())), 57, height / 2 - 60, 0xffffff);
        } else {
            guiGraphics.drawCenteredString(font, Component.translatable("text.ad_astra.text.catalog"), 57, height / 2 - 60, 0xffffff);
        }

    }

    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.renderBackground(guiGraphics);
        guiGraphics.fill(0, 0, width, height, 0xff000419);

        // Render diamond pattern lines
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        for (int i = -height; i <= width; i += 24) {
            bufferBuilder.vertex(i, 0, 0).color(0xff0f2559).endVertex();
            bufferBuilder.vertex(i + height, height, 0).color(0xff0f2559).endVertex();
        }

        for (int i = width + height; i >= 0; i -= 24) {
            bufferBuilder.vertex(i, 0, 0).color(0xff0f2559).endVertex();
            bufferBuilder.vertex(i - height, height, 0).color(0xff0f2559).endVertex();
        }
        tessellator.end();

        int currentPage = this.pageIndex;

        SolarSystem solarSystem = null;
        Set<PlanetRing> planetRings = new HashSet<>();
        for (SolarSystem system : AdAstraClient.solarSystems) {
            if(system.solarSystem().equals(selectedSolarSystem)){
                solarSystem = system;
            }
        }

        for (PlanetRing ring : AdAstraClient.planetRings) {
            if (ring.solarSystem().equals(selectedSolarSystem)  || this.currentCategory.parent() != null && ring.solarSystem().equals(selectedSolarSystem)) {
                planetRings.add(ring);
            }
        }

        // Universe
        if(pageIndex == 0) {
            ScreenUtils.addRotatingTexture(0, -125, 250, 250, new ResourceLocation(AdAstra.MOD_ID, "textures/environment/universe.png"), 0.6f, 250, guiGraphics);
        }

        // Galaxy
        if(pageIndex == 1) {
            this.currentCategory = new Category(new ResourceLocation(selectedGalaxy.toString()), new Category(new ResourceLocation(AdAstra.MOD_ID, "galaxy"), null));
            AdAstraClient.galaxies.stream().filter(g -> g.galaxy().equals(this.currentCategory.id()))
                .findFirst()
                .ifPresent(galaxy -> ScreenUtils.addRotatingTexture(0, -125, galaxy.scale(), galaxy.scale(), galaxy.texture(), 0.6f, galaxy.scale(), guiGraphics));
        }

        // Solar System
        if (pageIndex == 2){
            if(solarSystem != null){
                // Sun
                ScreenUtils.addTexture(new PoseStack(), (int) (((width + 5 ) - solarSystem.sunScale()) / 2), (int) (((height + 5) - solarSystem.sunScale()) / 2), (int) (solarSystem.sunScale() / 1.5), (int) (solarSystem.sunScale() / 1.5), solarSystem.sun(), guiGraphics);

                if(!planetRings.isEmpty()){
                    for (PlanetRing ring : planetRings) {
                        ScreenUtils.drawCircle(width / 2f, height / 2f, ring.radius() * 24, 75, solarSystem.ringColour());
                    }
                    for (PlanetRing ring : planetRings) {
                        float rotation = Util.getMillis() / ring.speed();
                        int coordinates = (int) (ring.radius() * 10.5 - (ring.scale() / 1.9));
                        ScreenUtils.addRotatingTexture(coordinates, coordinates, width, height, ring.texture(), rotation, ring.scale(), guiGraphics);
                    }
                }
            }
        }
        // Planet
        if (pageIndex == 3){

        }
        AdAstraClientEvents.RenderSolarSystemEvent.fire(guiGraphics, selectedSolarSystem, width, height);
    }


    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderButtons(graphics, mouseX, mouseY, partialTick);

        if(hasMultipleGalaxies && pageIndex > 0){
            backButton.visible = true;
        }
        else{
            if(hasMultipleSolarSystems && pageIndex > 1) {
                backButton.visible = true;
            } else {
                backButton.visible = false;
            }

        }

        addSpaceStatonButton.visible = pageIndex > 2 && selectedPlanet != null;

        // Prevent buttons from being pressed when outside view area.
        buttons.forEach(button -> button.active = button.getY() > height / 2 - 63 && button.getY() < height / 2 + 88);


    }

    public enum TooltipType {
        NONE,
        GALAXY,
        SOLAR_SYSTEM,
        CATEGORY,
        PLANET,
        ORBIT,
        SPACE_STATION
    }

}
