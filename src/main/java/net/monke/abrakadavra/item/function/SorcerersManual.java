package net.monke.abrakadavra.item.function;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.monke.abrakadavra.Abrakadavra;

public class SorcerersManual extends Item {
    public SorcerersManual(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack pItemStack = pPlayer.getItemInHand(pUsedHand);

        if (pLevel.isClientSide) {
            Abrakadavra.PROXY.openBookGUI(pItemStack);
        }

        return new InteractionResultHolder(InteractionResult.PASS, pItemStack);
    }
}
