package net.vechnimetel.industrialcastingcore.block.entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.dubhe.anvilcraft.api.power.IPowerProducer;
import dev.dubhe.anvilcraft.api.power.PowerGrid;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.vechnimetel.industrialcastingcore.util.ModLang;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KineticGeneratorBlockEntity extends KineticBlockEntity implements IPowerProducer {
    private static final float ENERGY_COEFFICIENT = 1.0f;
    private static final float STRESS_IMPACT = 192.0f;

    private PowerGrid grid;

    public KineticGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntityTypes.KINETIC_GENERATOR.get(), pPos, pBlockState);
    }

    @Override
    public float calculateStressApplied() {
        return STRESS_IMPACT;
    }

    @Override
    public int getOutputPower() {
        return (int)(Math.abs(this.calculateStressApplied()*this.getSpeed())*ENERGY_COEFFICIENT);
    }

    @Override
    public Level getCurrentLevel() {
        return this.level;
    }

    @Override
    public @NotNull BlockPos getPos() {
        return this.worldPosition;
    }

    @Override
    public void setGrid(@Nullable PowerGrid powerGrid) {
        this.grid = powerGrid;
    }

    @Override
    public @Nullable PowerGrid getGrid() {
        return this.grid;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        ModLang.builder().translate("tooltip.kinetic_generator.header").forGoggles(tooltip);
        ModLang.builder().translate("tooltip.kinetic_generator.generation",this.getOutputPower())
                .style(ChatFormatting.BLUE).forGoggles(tooltip,1);
        return true;
    }
}
