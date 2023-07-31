package net.monke.abrakadavra;

import net.minecraft.client.gui.screens.MenuScreens;
import net.monke.abrakadavra.entity.EntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.monke.abrakadavra.block.entity.ModBlockEntities;
import net.monke.abrakadavra.item.ModItems;
import net.monke.abrakadavra.block.ModBlocks;
import net.monke.abrakadavra.item.function.UpdateWandPacket;
import net.monke.abrakadavra.screen.ModMenuTypes;
import net.monke.abrakadavra.screen.RuneTableScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Abrakadavra.MOD_ID)
public class Abrakadavra
{
    private static final String NETWORK_PROTOCOL = "1.0";
//    public static final SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(
//            new ResourceLocation("main"),
//            () -> NETWORK_PROTOCOL,
//            s -> true,
//            s -> true
//    );

    public static final String MOD_ID = "abrakadavra";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public Abrakadavra() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);

        EntityTypes.ENTITY_TYPES.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
        private void clientSetup (final FMLClientSetupEvent event)
        {
        MenuScreens.register(ModMenuTypes.RUNE_TABLE_MENU.get(), RuneTableScreen::new);
    }
    private void setup(final FMLCommonSetupEvent event)
    {
//        int packetId = 0; // Choose a unique ID for your packet
//        NETWORK_CHANNEL.registerMessage(packetId, UpdateWandPacket.class, UpdateWandPacket::encode, UpdateWandPacket::decode, UpdateWandPacket::handle);
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
