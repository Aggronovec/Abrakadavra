package net.monke.abrakadavra.item.function;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireSpellItem extends Item {
    private boolean needtolearn = true;
    public String LEARNED_SPELLS_KEY = "LearnedSpells";
    public FireSpellItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        if(pPlayer.getLevel().isClientSide()) {
        if (!pLevel.isClientSide()) {
            learnSpell(pPlayer, "Fire Spell");
            pPlayer.getInventory().removeItem(pPlayer.getItemInHand(pUsedHand));
//            if (learnSpell(pPlayer, "AAALevitation Blast")) { //these lines were enabled in the first version where we didn't do antyhing on the client side, only on server's side
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
                pPlayer.displayClientMessage(new TextComponent("You have just learnt a Fire Spell!"), true);
                pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 100.0F, 100.0F);
            }
            else {
                pPlayer.displayClientMessage(new TextComponent("You already know the Fire Spell!"), true);
                pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 100.0F, 100.0F);
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
            String persistentDataString = persistentData.toString();
            pPlayer.sendMessage(new TextComponent(persistentDataString), Util.NIL_UUID);
//            needtolearn = false; // Disable this if you want to disable the mechanic of test of the client side for whether you can or can't learn the spell
            return false;
        } else {
//            needtolearn = true;
            String persistentDataString = persistentData.toString();
            pPlayer.sendMessage(new TextComponent(persistentDataString), Util.NIL_UUID); // These two lines write you into the chat whether the spell was already written in data
            persistentData.putBoolean(spellId, true); // These two lines write you into the chat whether the spell was already written in data
            playerData.put(LEARNED_SPELLS_KEY, persistentData);
            return true;
        }
    }
}