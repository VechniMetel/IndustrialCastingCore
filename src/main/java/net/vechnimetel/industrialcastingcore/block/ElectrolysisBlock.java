package net.vechnimetel.industrialcastingcore.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.vechnimetel.industrialcastingcore.block.entity.ElectrolysisBlockEntity;
import net.vechnimetel.industrialcastingcore.block.entity.ModBlockEntities;
import net.vechnimetel.industrialcastingcore.block.state.properties.ElectrolysisLightLevel;
import net.vechnimetel.industrialcastingcore.block.state.properties.ModBlockStateProperties;

public class ElectrolysisBlock extends Block implements IBE<ElectrolysisBlockEntity> {

    private static final EnumProperty<ElectrolysisLightLevel> LIGHT_LEVEL;
    private static final VoxelShape SHAPE;

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public ElectrolysisBlock() {
        super(BlockBehaviour.Properties.of().noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(LIGHT_LEVEL, ElectrolysisLightLevel.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return switch (state.getValue(LIGHT_LEVEL)) {
            case BRIGHT -> 14;
            case DIM -> 7;
            default -> 0;
        };
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult result) {
        if(level.isClientSide()) return InteractionResult.SUCCESS;
        if(level.getBlockEntity(pos) instanceof ElectrolysisBlockEntity blockEntity)
            return blockEntity.changeLens((ServerPlayer) player);
        return InteractionResult.PASS;
    }

    @Override
    public Class<ElectrolysisBlockEntity> getBlockEntityClass() {
        return ElectrolysisBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ElectrolysisBlockEntity> getBlockEntityType() {
        return ModBlockEntities.ELECTROLYSIS.get();
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState blockState) {

    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(level.isClientSide()) return;
        if(newState.getBlock() == blockState.getBlock()) return;
        if(level.getBlockEntity(pos) instanceof ElectrolysisBlockEntity blockEntity) {
            blockEntity.getLevel().addFreshEntity(new ItemEntity(blockEntity.getLevel(),
                    blockEntity.getBlockPos().getX(),
                    blockEntity.getBlockPos().getY(),
                    blockEntity.getBlockPos().getZ(),
                    blockEntity.getLensItemCopy()));
        }
        super.onRemove(blockState, level, pos, newState, movedByPiston);
    }

    static {
        LIGHT_LEVEL = ModBlockStateProperties.LIGHT_LEVEL;
        SHAPE = Shapes.or(
                Block.box(1, 12, 1, 15, 16, 15),
                Block.box(5, 4, 5, 11, 12, 11),
                Block.box(3, 4, 3, 5, 12, 5),
                Block.box(3, 4, 11, 5, 12, 13),
                Block.box(11,4,3,13,12,5),
                Block.box(11,4,11,13,12,13),
                Block.box(3,2,3,13,4,13),
                Block.box(6,0,6,7,2,9),
                Block.box(9,0,7,10,2,10),
                Block.box(7,0,6,10,2,7),
                Block.box(6,0,9,9,2,10),
                Block.box(7,1,7,9,1,9)
        );
    }
}
