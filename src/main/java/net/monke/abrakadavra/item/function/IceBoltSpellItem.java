package net.monke.abrakadavra.item.function;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IceBoltSpellItem extends Item {
    public IceBoltSpellItem(Properties properties) {
        super(properties);
    }
    public String LEARNED_SPELLS_KEY = "LearnedSpells";

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pPlayer.getLevel().isClientSide()) {
            CompoundTag tag = pPlayer.getPersistentData();
            ListTag learnedSpellsTag = tag.getList(LEARNED_SPELLS_KEY,8);
            if (learnedSpellsTag.contains(StringTag.valueOf("Ice Bolt"))) {
                pPlayer.displayClientMessage(new TextComponent("You already know the ice bolt spell!"), true);
                pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN,1.0F, 1.0F);
            }
            else {
                learnedSpellsTag.add(StringTag.valueOf("Ice Bolt")); // Replace "YourSpellId" with the actual spell identifier
                tag.put(LEARNED_SPELLS_KEY, learnedSpellsTag);
                pPlayer.displayClientMessage(new TextComponent("You have just learnt an Ice Bolt!"), true);
                pPlayer.getItemInHand(pUsedHand).shrink(1);
            }
            String persistentDataString = tag.toString();
            pPlayer.sendMessage(new TextComponent(persistentDataString), Util.NIL_UUID);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

}