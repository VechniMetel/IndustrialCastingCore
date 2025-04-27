package net.vechnimetel.industrialcastingcore.block;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.vechnimetel.industrialcastingcore.block.entity.KineticGeneratorBlockEntity;
import net.vechnimetel.industrialcastingcore.block.entity.ModBlockEntityTypes;

public class KineticGeneratorBlock extends HorizontalKineticBlock implements IBE<KineticGeneratorBlockEntity> {
    public KineticGeneratorBlock() {
        super(BlockBehaviour.Properties.of().noOcclusion());
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public Class<KineticGeneratorBlockEntity> getBlockEntityClass() {
        return KineticGeneratorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends KineticGeneratorBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.KINETIC_GENERATOR.get();
    }
}
