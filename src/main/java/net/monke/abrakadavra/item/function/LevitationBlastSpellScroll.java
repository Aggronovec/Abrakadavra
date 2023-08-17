package net.monke.abrakadavra.item.function;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.monke.abrakadavra.networking.ModMessages;
import net.monke.abrakadavra.networking.packet.IceBoltScrollPacket;
import net.monke.abrakadavra.networking.packet.LevitationScrollPacket;

public class LevitationBlastSpellScroll extends Item {
    public LevitationBlastSpellScroll(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) { // Only send the packet from the client side
            ModMessages.sendToServer(new LevitationScrollPacket());
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}