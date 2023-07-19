package net.monke.abrakadavra.item.function;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wand extends Item {
    public Wand(Properties pProperties) {
        super(pProperties);
    }
    public static final String DEFAULT_SPELL = "Ice Bolt"; // Default spell if no spell is assigned
    public static void setSpellAssignedToWand(ItemStack wandStack, String spellName) {
        if (wandStack != null) {
            CompoundTag wandTag = wandStack.getOrCreateTag();
            wandTag.putString("SpellAssignedToWand", spellName);
        }
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag wandTag = pStack.getOrCreateTag();
        String spellName = wandTag.getString("SpellAssignedToWand");

        if (spellName.isEmpty()) {
            spellName = DEFAULT_SPELL;
        }
        pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand.tooltip").append(new TextComponent(spellName)));
    }
}
