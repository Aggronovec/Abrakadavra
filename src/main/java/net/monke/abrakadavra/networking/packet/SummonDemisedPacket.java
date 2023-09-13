package net.monke.abrakadavra.networking.packet;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.monke.abrakadavra.item.ModItems;

import java.util.function.Supplier;

public class SummonDemisedPacket {
    public SummonDemisedPacket() {}

    public SummonDemisedPacket(FriendlyByteBuf buf) {
            }

    public void toBytes(FriendlyByteBuf buf) {

    }
    public boolean handle (Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            player.containerMenu.setItem(36, 1, new ItemStack(ModItems.WAND_SUMMON_DEMISED.get()));
//            player.getInventory().setItem(41, new ItemStack(ModItems.WAND_LEVITATION_BLAST.get()));
//            player.getInventory().add(new ItemStack(ModItems.WAND_LEVITATION_BLAST.get()));
            level.playSound(null, player.getOnPos(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS,
                    level.random.nextFloat()*0.8F+1.5F, level.random.nextFloat()*0.1F+0.9F);
//            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,posX, posY, posZ,100,posX+5, posY+5, posZ+5,1);
        });
        return true;
    }
}
