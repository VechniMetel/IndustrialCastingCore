package net.vechnimetel.industrialcastingcore.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum ElectrolysisLightLevel implements StringRepresentable {
    NONE("none"),
    DIM("dim"),
    BRIGHT("bright");

    private final String name;

    ElectrolysisLightLevel(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
