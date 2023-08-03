package net.monke.abrakadavra.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WandUpdatePacket {
    private int selectedSlot;
    private ItemStack newWandStack;

    public WandUpdatePacket() {}

    public WandUpdatePacket(int selectedSlot, ItemStack newWandStack) {
        this.selectedSlot = selectedSlot;
        this.newWandStack = newWandStack;
    }

    public static void encode(WandUpdatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.selectedSlot);
        buffer.writeItem(packet.newWandStack);
    }

    public static WandUpdatePacket decode(FriendlyByteBuf buffer) {
        int selectedSlot = buffer.readInt();
        ItemStack newWandStack = buffer.readItem();
        return new WandUpdatePacket(selectedSlot, newWandStack);
    }

    public static void handle(WandUpdatePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                // Update the player's inventory with the new wand item
                // The selectedSlot can be used to determine which slot in the inventory to update
                player.getInventory().setItem(packet.selectedSlot, packet.newWandStack);
            }
        });
        context.setPacketHandled(true);
    }
}