package net.monke.abrakadavra.item.function;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class IceBoltSpellScroll extends Item {

    public IceBoltSpellScroll(Properties pProperties) {
        super(pProperties);
    }

    private boolean used;
    public boolean isUsed() {
        return used;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if(!used) {
            pPlayer.displayClientMessage(new TextComponent("You have just learnt the Ice Bolt Spell!"), true);

            used = true;
        }
        if(used) {
            pPlayer.displayClientMessage(new TextComponent("WHY THIS WILL NOT WORK NOOOOO"), false);
        }


        return super.use(pLevel, pPlayer, pHand);
    }
}