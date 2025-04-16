package net.vechnimetel.industrialcastingcore.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vechnimetel.industrialcastingcore.block.ModBlocks;
import net.vechnimetel.industrialcastingcore.block.entity.ElectrolysisBlockEntity;
import net.vechnimetel.industrialcastingcore.util.ModLang;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElectrolysisBlockItem extends BlockItem {

    public ElectrolysisBlockItem() {
        super(ModBlocks.ELECTROLYSIS.get(), new Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        ModLang.builder().translate("tooltip.electrolysis.required_laser_level",
                        ElectrolysisBlockEntity.LASER_LEVEL_REQUIREMENT).style(ChatFormatting.YELLOW).addTo(tooltip);
    }
}
