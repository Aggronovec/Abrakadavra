package net.monke.abrakadavra.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.monke.abrakadavra.Abrakadavra;
import net.monke.abrakadavra.client.render.IceBoltRendered;
import net.monke.abrakadavra.client.render.FireBoltRendered;
import net.monke.abrakadavra.entity.EntityInit;


    @Mod.EventBusSubscriber(modid = Abrakadavra.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ClientSetup {
        @SubscribeEvent
        public static void doSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(EntityInit.ICE_BOLT_PROJECTILE.get(), IceBoltRendered::new);
            EntityRenderers.register(EntityInit.FIRE_BOLT_PROJECTILE.get(), FireBoltRendered::new);
        }
    }

