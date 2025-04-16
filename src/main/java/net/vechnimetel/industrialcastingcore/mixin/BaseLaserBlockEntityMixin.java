package net.vechnimetel.industrialcastingcore.mixin;

import dev.dubhe.anvilcraft.block.entity.BaseLaserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.vechnimetel.industrialcastingcore.block.entity.ElectrolysisBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseLaserBlockEntity.class)
public abstract class BaseLaserBlockEntityMixin {
    @Shadow(remap = false) public BlockPos irradiateBlockPos;

    @Shadow(remap = false) public int laserLevel;

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    public void tick(Level level, CallbackInfo ci) {
        if(!level.isClientSide()) {
            if(this.irradiateBlockPos != null &&
                    level.getBlockEntity(this.irradiateBlockPos) instanceof ElectrolysisBlockEntity blockEntity) {
                blockEntity.laserHit(this.laserLevel);
            }
        }
    }
}
