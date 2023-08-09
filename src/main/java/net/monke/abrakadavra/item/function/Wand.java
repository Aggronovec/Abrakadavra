package net.monke.abrakadavra.item.function;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.monke.abrakadavra.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wand extends Item {
    public Wand(Properties pProperties) {
        super(pProperties);
    }
    private static final String SPELL_NBT_KEY = "SpellAssignedToWand ";
    public static final String DEFAULT_SPELL = ""; // Default spell if no spell is assigned
    public static void setSpellAssignedToWand(ItemStack wandStack, String spellName) {
        CompoundTag nbtData = new CompoundTag();
//        CompoundTag wandTag = wandStack.getOrCreateTag();
        nbtData.putString(SPELL_NBT_KEY, spellName);
        wandStack.setTag(nbtData);
    }
    public static String getSpellAssignedToWand(ItemStack wandStack) {
        CompoundTag wandTag = wandStack.getTag();
        if (wandTag != null && wandTag.contains(SPELL_NBT_KEY)) {
            return wandTag.getString(SPELL_NBT_KEY);
        }
        return DEFAULT_SPELL;
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String spellName = getSpellAssignedToWand(pStack);
        switch (getRegistryName().toString()) {
            case "abrakadavra:wand":
                    pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand.tooltip")); // + .append(new TextComponent(spellName)
                    break;
            case "abrakadavra:wand_ice_bolt":
                    pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand_ice_bolt.tooltip")); // + .append(new TextComponent(spellName)
                    break;
            case "abrakadavra:wand_levitation_blast":
                    pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand_levitation_blast.tooltip")); // + .append(new TextComponent(spellName)
                    break;
            case "abrakadavra:wand_summon_demised":
                    pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand_summon_demised.tooltip")); // + .append(new TextComponent(spellName)
                    break;
        }
    }
    @Override
    public boolean isFoil(ItemStack pStack) {
        return  pStack.getItem() == ModItems.WAND_ICE_BOLT.get() ||
                pStack.getItem() == ModItems.WAND_LEVITATION_BLAST.get() ||
                pStack.getItem() == ModItems.WAND_SUMMON_DEMISED.get();
        //        return pStack.hasTag();
    }
}