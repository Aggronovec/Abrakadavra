package net.monke.abrakadavra.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;
import net.monke.abrakadavra.effect.ModEffects;
import net.monke.abrakadavra.util.AdvancementUtil;

import java.util.function.Supplier;

public class SummonDemisedScrollPacket {
    public SummonDemisedScrollPacket() {}
    public SummonDemisedScrollPacket(FriendlyByteBuf buf) {
            }
    public void toBytes(FriendlyByteBuf buf) {
    }
    private String spellId = "Summon Demised";
    private String LEARNED_SPELLS_KEY = "LearnedSpells";
    public boolean handle (Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            CompoundTag playerData = player.getPersistentData();
            if (!playerData.contains(LEARNED_SPELLS_KEY)) {
                playerData.put(LEARNED_SPELLS_KEY, new CompoundTag());
            }
            CompoundTag persistentData = playerData.getCompound(LEARNED_SPELLS_KEY);
            if (persistentData.contains(spellId)) {
//                Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
//                        new TranslatableComponent(spellId + " already known!"), new TranslatableComponent("")));
                player.displayClientMessage(new TranslatableComponent("You already know the " + spellId + " spell!"), true);
                level.playSound(null, player.getOnPos(), SoundEvents.BOOK_PUT, SoundSource.PLAYERS,
                        0.8F, level.random.nextFloat() * 0.1F + 0.9F);
            } else {
                persistentData.putBoolean(spellId, true); // These two lines write you into the chat whether the spell was already written in data
                playerData.put(LEARNED_SPELLS_KEY, persistentData);
                player.displayClientMessage(new TranslatableComponent("You have just learnt the " + spellId + " spell!"), true);
//                Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
//                        new TranslatableComponent(spellId + " learnt!"), new TranslatableComponent("")));
                player.getInventory().removeItem(player.getItemInHand(InteractionHand.MAIN_HAND));
                level.playSound(null, player.getOnPos(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS,
                        0.8F, level.random.nextFloat() * 0.1F + 0.9F);
                if (AdvancementUtil.TestForWizard(player, persistentData)) {
                    player.addEffect(new MobEffectInstance(ModEffects.WIZARD.get(), 30 * 20, 0));
                }
            }
        });
        return true;
    }
}