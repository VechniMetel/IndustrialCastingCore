package net.vechnimetel.industrialcastingcore.block.entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.equipment.goggles.IHaveHoveringInformation;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.vechnimetel.industrialcastingcore.block.ElectrolysisBlock;
import net.vechnimetel.industrialcastingcore.block.state.properties.ElectrolysisLightLevel;
import net.vechnimetel.industrialcastingcore.block.state.properties.ModBlockStateProperties;
import net.vechnimetel.industrialcastingcore.recipe.ElectrolysisRecipe;
import net.vechnimetel.industrialcastingcore.recipe.ModRecipeTypes;
import net.vechnimetel.industrialcastingcore.util.ModLang;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElectrolysisBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation, IHaveHoveringInformation {

    public static final int PROCESSING_TIME = 20;
    public static final int LASER_LEVEL_REQUIREMENT = 9;
    public static final int LASER_TICK_BUFFER = 1;
    public static final ParticleOptions PARTICLE = ParticleTypes.FLAME;
    protected ItemStack lensItem = ItemStack.EMPTY;
    protected final List<StoredLaser> storedLasers = new ArrayList<>();
    protected BeltProcessingBehaviour beltProcessing;
    protected int processedTicks = 0;
    protected boolean processing = false;
    protected int laserLevel = 0;
    private static final EnumProperty<ElectrolysisLightLevel> LIGHT_LEVEL = ModBlockStateProperties.LIGHT_LEVEL;

    public ElectrolysisBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ELECTROLYSIS.get(), pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        this.beltProcessing = (new BeltProcessingBehaviour(this))
                .whenItemEnters(this::onItemRecieved).whileItemHeld(this::whenItemHeld);
        behaviours.add(beltProcessing);
    }

    protected ProcessingResult onItemRecieved(TransportedItemStack transported,
                                              TransportedItemStackHandlerBehaviour handler) {
        if(handler.blockEntity.isVirtual()) {
            return ProcessingResult.PASS;
        } else if (!this.isProcessable(transported.stack)) {
            this.stopProcessing();
            return ProcessingResult.PASS;
        } else {
            this.startProcessing();
            return ProcessingResult.HOLD;
        }
    }

    protected  ProcessingResult whenItemHeld(TransportedItemStack transported,
                                             TransportedItemStackHandlerBehaviour handler) {
        if(handler.blockEntity.isVirtual()) {
            return ProcessingResult.PASS;
        }else if(this.processedTicks < PROCESSING_TIME) {
            return ProcessingResult.HOLD;
        } else if (!this.isProcessable(transported.stack)) {
            this.stopProcessing();
            return ProcessingResult.PASS;
        } else {
            ItemStack out = this.getResult(transported.stack);
            if(out != null) {
                TransportedItemStack result = transported.copy();
                TransportedItemStack held = null;
                result.stack = out;
                transported.stack.shrink(1);
                if(!transported.stack.isEmpty()) {
                    held = transported.copy();
                }
                List<TransportedItemStack> results = new ArrayList<>();
                results.add(result);
                handler.handleProcessingOnItem(transported,
                        TransportedItemStackHandlerBehaviour.TransportedResult.convertToAndLeaveHeld(results,held));
                this.stopProcessing();
            }
            return ProcessingResult.HOLD;
        }
    }

    public InteractionResult changeLens(ServerPlayer player) {
        if(this.isProcessing()) return InteractionResult.PASS;
        if(player.getMainHandItem().isEmpty() && !this.lensItem.isEmpty()) {
            player.setItemInHand(InteractionHand.MAIN_HAND,this.lensItem);
            this.lensItem = ItemStack.EMPTY;
            this.notifyUpdate();
            return InteractionResult.SUCCESS;
        }
        if(!player.getMainHandItem().isEmpty() && this.lensItem.isEmpty()) {
            ItemStack copy = player.getMainHandItem().copy();
            copy.setCount(1);
            this.lensItem = copy;
            player.getMainHandItem().shrink(1);
            this.notifyUpdate();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public ItemStack getLensItemCopy() {
        return this.lensItem.copy();
    }

    protected ItemStack getResult(ItemStack stack) {
        RecipeWrapper wrapper = new RecipeWrapper(new ItemStackHandler(2));
        wrapper.setItem(0,stack);
        wrapper.setItem(1,this.lensItem);
        Optional<ElectrolysisRecipe> assemblyRecipe =
                SequencedAssemblyRecipe.getRecipe(this.level,wrapper,
                        ModRecipeTypes.ELECTROLYSIS.getType(),ElectrolysisRecipe.class);
        if(assemblyRecipe.isPresent()) {
            return assemblyRecipe.get().rollResults().get(0);
        } else {
            Optional<Recipe<RecipeWrapper>> recipe =
                this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.ELECTROLYSIS.getType(),wrapper,this.level);
            if(recipe.isPresent()) {
                return ((ElectrolysisRecipe) recipe.get()).rollResults().get(0);
            } else {
                return null;
            }
        }
    }

    protected boolean isProcessable(ItemStack stack) {
        return this.getResult(stack) != null;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        ModLang.builder().translate("tooltip.electrolysis.header").forGoggles(tooltip);
        ModLang.builder().translate("tooltip.electrolysis.required_laser_level",LASER_LEVEL_REQUIREMENT)
                .style(ChatFormatting.YELLOW).forGoggles(tooltip,1);
        ModLang.builder().translate("tooltip.electrolysis.current_laser_level",this.laserLevel)
                .style(ChatFormatting.GREEN).forGoggles(tooltip,1);
        return true;
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putInt("ProcessedTicks", processedTicks);
        tag.putBoolean("Processing", processing);
        tag.putInt("LaserLevel", laserLevel);
        tag.put("Lens",lensItem.serializeNBT());
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        this.processedTicks = tag.getInt("ProcessedTicks");
        this.processing = tag.getBoolean("Processing");
        this.laserLevel = tag.getInt("LaserLevel");
        if(tag.contains("Lens")) {
            this.lensItem = ItemStack.of(tag.getCompound("Lens"));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.level.isClientSide()) {
            if(this.isProcessing()) {
                this.process();
            }
            int tempLaserLevel = 0;
            List<StoredLaser> toRemove = new ArrayList<>();
            for (StoredLaser storedLaser : storedLasers) {
                if (storedLaser.ticks > 0) {
                    tempLaserLevel = Math.max(tempLaserLevel, storedLaser.laserLevel);
                    storedLaser.ticks--;
                } else {
                    toRemove.add(storedLaser);
                }
            }
            this.storedLasers.removeAll(toRemove);
            this.changeLaserLevel(tempLaserLevel);
            BlockState state = level.getBlockState(worldPosition);
            if(state.getBlock() instanceof ElectrolysisBlock &&
                    state.getValue(LIGHT_LEVEL) != this.getLightLevel()) {
                level.setBlock(worldPosition,state.setValue(LIGHT_LEVEL,this.getLightLevel()),2);
            }
        }
    }

    protected void changeLaserLevel(int laserLevel) {
        if(this.laserLevel != laserLevel) {
            this.laserLevel = laserLevel;
            this.notifyUpdate();
        }
    }

    public ElectrolysisLightLevel getLightLevel() {
        if(this.laserLevel >= LASER_LEVEL_REQUIREMENT) return ElectrolysisLightLevel.BRIGHT;
        else if(this.laserLevel > 0) return ElectrolysisLightLevel.DIM;
        else return ElectrolysisLightLevel.NONE;
    }

    public void laserHit(int level) {
        storedLasers.add(new StoredLaser(level));
    }

    protected void spawnParticles() {
        if(!this.isVirtual()) {
            Vec3 vec = VecHelper.getCenterOf(this.worldPosition);
            vec = vec.subtract(0,1.6875F,0);
            ((ServerLevel)this.level).sendParticles(
                    PARTICLE,vec.x,vec.y,vec.z,
                    1,0,1,0,0.1
            );
        }
    }

    protected void process() {
        if(this.laserLevel >= LASER_LEVEL_REQUIREMENT) {
            this.processedTicks++;
            if(!this.level.isClientSide()) {
                this.spawnParticles();
            }
        }
        if(this.processedTicks > PROCESSING_TIME) {
            this.stopProcessing();
        }
    }

    protected void startProcessing() {
        processing = true;
        processedTicks = 0;
    }

    protected void stopProcessing() {
        processing = false;
        processedTicks = 0;
    }

    protected boolean isProcessing() {
        return processing;
    }

    private class StoredLaser {
        private final int laserLevel;
        private int ticks;

        private StoredLaser(int level) {
            this.laserLevel = level;
            this.ticks = LASER_TICK_BUFFER;
        }
    }
}

