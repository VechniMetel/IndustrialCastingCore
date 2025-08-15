package net.vechnimetel.industrialcastingcore.item;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class LeafLanguagePendantItem extends Item {
    public LeafLanguagePendantItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilityProvider() {
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
                if(capability == CapabilityManager.get(new CapabilityToken<ICurio>() {})) {
                    return LazyOptional.of(() -> new ICurio() {

                        @Override
                        public ItemStack getStack() {
                            return stack;
                        }

                        @Override
                        public void curioTick(SlotContext slotContext) {
                            if(slotContext.entity().tickCount % 20 == 0 &&
                                    slotContext.entity().level().getBiome(slotContext.entity().blockPosition()).is(Biomes.FOREST)) {
                                slotContext.entity().addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
                            }
                        }
                    }).cast();
                }
                return LazyOptional.empty();
            }
        };
    }
}
