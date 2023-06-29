package net.monke.abrakadavra.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.monke.abrakadavra.Abrakadavra;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Abrakadavra.MOD_ID);

    public static final RegistryObject<Item> WAND_HANDLE = ITEMS.register("wand_handle", () -> new Item(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            stacksTo(16)));
    public static final RegistryObject<Item> WAND = ITEMS.register("wand", () -> new Item(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            durability(150)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
