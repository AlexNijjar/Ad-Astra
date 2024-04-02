package earth.terrarium.adastra.common.compat.rei.widgets;

import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ReiFluidBarWidget extends Widget {

    private final Rectangle bounds;
    private final boolean gain;
    private final long perTick;
    private final int cookTime;
    private final long capacity;
    private final Fluid fluid;

    public ReiFluidBarWidget(Point point, boolean generate, long capacity, int cookTime, FluidHolder fluid) {
        this.bounds = new Rectangle(new Rectangle(point.x, point.y, GuiUtils.FLUID_BAR_WIDTH, GuiUtils.FLUID_BAR_HEIGHT));
        this.gain = generate;
        this.perTick = fluid.getFluidAmount();
        this.cookTime = cookTime;
        this.capacity = capacity;
        this.fluid = fluid.getFluid();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        long time = Objects.requireNonNull(Minecraft.getInstance().level).getGameTime();
        long amount = (time % (capacity / perTick) * perTick);
        long fluidAmount = gain ? amount : capacity - amount;

        GuiUtils.drawFluidBar(
            graphics,
            mouseX,
            mouseY,
            this.bounds.x,
            this.bounds.y,
            FluidHolder.of(fluid, fluidAmount),
            capacity,
            TooltipUtils.getTicksPerIterationComponent(cookTime),
            gain ? TooltipUtils.getFluidGenerationPerIterationComponent(perTick) : TooltipUtils.getFluidUsePerIterationComponent(perTick)
        );
    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return List.of();
    }
}
