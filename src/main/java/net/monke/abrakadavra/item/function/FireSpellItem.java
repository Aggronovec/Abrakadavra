package net.monke.abrakadavra.item.function;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireSpellItem extends Item {
    public FireSpellItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pPlayer.getLevel().isClientSide()) {
            pPlayer.displayClientMessage(new TextComponent("You have just learnt an Ice Bolt!"), true);
            pPlayer.getItemInHand(pUsedHand).shrink(1);
            pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN,1.0F, 1.0F);

        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

}