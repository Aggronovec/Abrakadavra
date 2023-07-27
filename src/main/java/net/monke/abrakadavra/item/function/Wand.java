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
    public static final String DEFAULT_SPELL = "Ice Bolt"; // Default spell if no spell is assigned
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
        pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand.tooltip").append(new TextComponent(spellName)));
    }
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        if(!pLevel.isClientSide()) {
//            ItemStack wandStack = pPlayer.getItemInHand(pUsedHand);
//            Wand.setSpellAssignedToWand(wandStack, "AGGRON IS SUPER");
//        }
//        return super.use(pLevel, pPlayer, pUsedHand);
//    }
    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.hasTag();
    }
}