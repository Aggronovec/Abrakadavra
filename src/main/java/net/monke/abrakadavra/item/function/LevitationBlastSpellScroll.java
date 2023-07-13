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

public class LevitationBlastSpellScroll extends Item {
    public String LEARNED_SPELLS_KEY = "LearnedSpells";
    private boolean needtolearn = true;
    public LevitationBlastSpellScroll(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        if(pPlayer.getLevel().isClientSide()) {
        if (!pLevel.isClientSide()) {
            learnSpell(pPlayer, "Levitation Blast");
            pPlayer.getInventory().removeItem(pPlayer.getItemInHand(pUsedHand));
        }
        else {
            if (needtolearn) {
                pPlayer.displayClientMessage(new TextComponent("You have just learnt a Levitation Blast!"), true);

                pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0F, 1.0F);
            }
            else {
                pPlayer.displayClientMessage(new TextComponent("You already know the Levitation Blast spell!"), true);
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