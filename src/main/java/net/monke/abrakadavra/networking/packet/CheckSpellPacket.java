package net.monke.abrakadavra.networking.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CheckSpellPacket {
    public CheckSpellPacket() {}
    public CheckSpellPacket(FriendlyByteBuf buf) {
            }
    public void toBytes(FriendlyByteBuf buf) {
    }
    private String LEARNED_SPELLS_KEY = "LearnedSpells";
    public static boolean HasLevitationBlastspell = false;
    public static boolean HasSummonDemisedSpell = false;
    public static boolean HasIceBoltSpell = false;
    public boolean handle (Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            CompoundTag playerData = player.getPersistentData();
            CompoundTag persistentData = playerData.getCompound(LEARNED_SPELLS_KEY);
            if (persistentData.contains("Ice Bolt")) {
                HasIceBoltSpell = true;}
            if (persistentData.contains("Levitation Blast")) {
                HasLevitationBlastspell = true;}
            if (persistentData.contains("Summon Demised")) {
                HasSummonDemisedSpell = true;}
            player.displayClientMessage(new TextComponent(playerData.getAsString()), true);
        });
        return true;
    }
}
