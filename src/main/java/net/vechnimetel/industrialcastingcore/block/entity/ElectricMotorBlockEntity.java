package net.vechnimetel.industrialcastingcore.block.entity;

import com.simibubi.create.content.kinetics.BlockStressValues;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.dubhe.anvilcraft.api.power.IPowerConsumer;
import dev.dubhe.anvilcraft.api.power.PowerGrid;
import dev.dubhe.anvilcraft.block.RubyLaserBlock;
import dev.dubhe.anvilcraft.inventory.SliderMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.vechnimetel.industrialcastingcore.block.ElectricMotorBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElectricMotorBlockEntity extends GeneratingKineticBlockEntity implements IPowerConsumer, MenuProvider {

    private PowerGrid grid;
    private int power = 0;
    private int prevPower = 0;
    private boolean on = false;
    private final float STRESS_CAPACITY = 1.0F;
    private float ENERGY_COEFFICIENT = 1.0F;

    public ElectricMotorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ELECTRIC_MOTOR.get(), pos, state);
    }

    @Override
    public float calculateAddedStressCapacity() {
        return STRESS_CAPACITY;
    }

    @Override
    public float getGeneratedSpeed() {
        if(!this.on) return 0;
        return convertToDirection(power*ENERGY_COEFFICIENT/this.calculateAddedStressCapacity(),
                this.getBlockState().getValue(ElectricMotorBlock.HORIZONTAL_FACING));
    }

    public int getPower() {
        return power;
    }

    @Override
    public Level getCurrentLevel() {
        return level;
    }

    @Override
    public @NotNull BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public void setGrid(@Nullable PowerGrid powerGrid) {
        this.grid = powerGrid;
    }

    @Override
    public @Nullable PowerGrid getGrid() {
        return grid;
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt("Power", power);
        compound.putBoolean("On", on);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        power = compound.getInt("Power");
        on = compound.getBoolean("On");
    }

    @Override
    public void tick() {
        super.tick();

        if(!this.level.isClientSide) {
            if(prevPower != power) {
                prevPower = power;
                this.notifyUpdate();
                this.updateGeneratedRotation();
            }
            if(on != ((this.grid!=null) && this.grid.isWork())) {
                on = (this.grid!=null) && this.grid.isWork();
                this.notifyUpdate();
                this.updateGeneratedRotation();
            }
        }
    }

    @Override
    public int getInputPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.industrial_casting_core.electric_motor");
    }



    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SliderMenu(i,0,256,this::setPower);
    }
}
