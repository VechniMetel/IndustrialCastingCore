package net.vechnimetel.industrialcastingcore.block.state.properties;

import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockStateProperties {

    public static final EnumProperty<ElectrolysisLightLevel> LIGHT_LEVEL;

    static {
        LIGHT_LEVEL = EnumProperty.create("light_level", ElectrolysisLightLevel.class);
    }
}
