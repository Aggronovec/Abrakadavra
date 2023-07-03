package net.monke.abrakadavra.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.monke.abrakadavra.Abrakadavra;
import net.monke.abrakadavra.item.function.FireSpellItem;
import net.monke.abrakadavra.item.function.IceBoltSpellItem;
import net.monke.abrakadavra.item.function.LevitationBlastSpellItem;
import net.monke.abrakadavra.item.function.Wand;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Abrakadavra.MOD_ID);

    public static final RegistryObject<Item> WAND_HANDLE = ITEMS.register("wand_handle", () -> new Item(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            stacksTo(16)));
    public static final RegistryObject<Item> WAND = ITEMS.register("wand", () -> new Wand(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            durability(150)));
    public static final RegistryObject<Item> ICE_BOLT_SPELL_SCROLL = ITEMS.register("ice_bolt_spell_scroll", () -> new IceBoltSpellItem(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            stacksTo(1)));
    public static final RegistryObject<Item> LEVITATION_BLAST_SPELL_SCROLL = ITEMS.register("levitation_blast_spell_scroll", () -> new LevitationBlastSpellItem(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            stacksTo(1)));
    public static final RegistryObject<Item> FIRE_SPELL_SCROLL = ITEMS.register("fire_spell_scroll", () -> new FireSpellItem(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
