package net.monke.abrakadavra.item.function;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LevitationBlastSpellItem extends Item {
    public String LEARNED_SPELLS_KEY = "LearnedSpells";
    boolean needtolearn = true;
    public LevitationBlastSpellItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        if(pPlayer.getLevel().isClientSide()) {
        if (!pLevel.isClientSide()) {
            learnSpell(pPlayer, "CCCLevitation Blast");
//            if (learnSpell(pPlayer, "AAALevitation Blast")) {
//                pPlayer.displayClientMessage(new TextComponent("You have just learnt a levitation blast!"), true);
//                pPlayer.getItemInHand(pUsedHand).shrink(1);
//                pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0F, 1.0F);
//            } else {
//                pPlayer.displayClientMessage(new TextComponent("You already know the levitation blast spell!"), true);
//                pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0F, 1.0F);
//                        }
        }
        else {
            if (needtolearn) {
                pPlayer.displayClientMessage(new TextComponent("You have just learnt a levitation blast!"), true);
                pPlayer.getItemInHand(pUsedHand).shrink(1);
                pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0F, 1.0F);
            }
            else {
                pPlayer.displayClientMessage(new TextComponent("You already know the levitation blast spell!"), true);
                pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0F, 1.0F);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
    private boolean learnSpell(Player pPlayer, String spellId) {
        CompoundTag playerData = pPlayer.getPersistentData();

        if (!playerData.contains(LEARNED_SPELLS_KEY)) {
            playerData.put(LEARNED_SPELLS_KEY, new CompoundTag());
        }
        CompoundTag persistentData = playerData.getCompound(LEARNED_SPELLS_KEY);
        if (persistentData.contains(spellId)) {
            needtolearn = false;
            return false;
        } else {
            needtolearn = true;
            persistentData.putBoolean(spellId, true);
            playerData.put(LEARNED_SPELLS_KEY, persistentData);
            return true;
        }
    }
}