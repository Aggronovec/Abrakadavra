package net.monke.abrakadavra.networking.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import net.monke.abrakadavra.sound.ModSounds;

import java.util.function.Supplier;

public class PlaceWandPacket {
    public PlaceWandPacket() {}
    public PlaceWandPacket(FriendlyByteBuf buf) {
            }
    public void toBytes(FriendlyByteBuf buf) {
    }
    private String LEARNED_SPELLS_KEY = "LearnedSpells";
    private String spellId = "Ice Bolt";
    public boolean handle (Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            level.playSound(null, player.getOnPos(), ModSounds.PLACE_WAND.get(), SoundSource.PLAYERS,
                    level.random.nextFloat() * 0.05F + 0.2F, level.random.nextFloat() * 0.1F + 0.9F);
        });
        return true;
    }
}
