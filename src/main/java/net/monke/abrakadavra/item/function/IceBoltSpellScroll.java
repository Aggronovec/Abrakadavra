package net.monke.abrakadavra.item.function;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.monke.abrakadavra.effect.ModEffects;
import net.monke.abrakadavra.networking.ModMessages;
import net.monke.abrakadavra.networking.packet.IceBoltScrollPacket;

public class IceBoltSpellScroll extends Item {
    public IceBoltSpellScroll(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) { // Only send the packet from the server side
            ModMessages.sendToServer(new IceBoltScrollPacket());
            pPlayer.addEffect(new MobEffectInstance(ModEffects.WIZARD.get(), 30*20, 0));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}