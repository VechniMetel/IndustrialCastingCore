package net.vechnimetel.industrialcastingcore.fluid.type;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.vechnimetel.industrialcastingcore.util.ModFluidUtils;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class SugarcaneJuiceFluidType extends FluidType {

    public SugarcaneJuiceFluidType() {
        super(FluidType.Properties.create().lightLevel(2).viscosity(5).density(15));
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ModFluidUtils.WATER_STILL_RL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return ModFluidUtils.WATER_FLOWING_RL;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return ModFluidUtils.WATER_OVERLAY_RL;
            }

            @Override
            public int getTintColor() {
                return 0xFFDECA51;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                    int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return new Vector3f(222f/255f, 202f/255f, 208f/255f);
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance,
                                        float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(1f);
                RenderSystem.setShaderFogEnd(6f);
            }
        });
    }
}
