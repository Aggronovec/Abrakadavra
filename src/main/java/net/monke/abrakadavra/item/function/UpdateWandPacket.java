package net.monke.abrakadavra.item.function;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateWandPacket {

    private final String spellName;

    public UpdateWandPacket(String spellName) {
        this.spellName = spellName;
    }

    public static void encode(UpdateWandPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUtf(packet.spellName);
    }

    public static UpdateWandPacket decode(FriendlyByteBuf buffer) {
        String spellName = buffer.readUtf();
        return new UpdateWandPacket(spellName);
    }

    public static void handle(UpdateWandPacket packet, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            // Handle the received packet on the client side
            // In this case, you can update the spell on the client's wand item
            // For example, you can call the setSpellAssignedToWand method again with packet.spellName
        });
        ctx.setPacketHandled(true);
    }
}
