package net.monke.abrakadavra.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.monke.abrakadavra.Abrakadavra;
import net.monke.abrakadavra.item.function.SummonDemisedSpellScroll;
import net.monke.abrakadavra.item.function.IceBoltSpellScroll;
import net.monke.abrakadavra.item.function.LevitationBlastSpellScroll;
import net.monke.abrakadavra.item.function.Wand;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Abrakadavra.MOD_ID);

    //WAND

    public static final RegistryObject<Item> WAND = ITEMS.register("wand", () -> new Wand(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            durability(150)));
    public static final RegistryObject<Item> WAND_ICE_BOLT = ITEMS.register("wand_ice_bolt", () -> new Wand(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            durability(150)));
    public static final RegistryObject<Item> WAND_LEVITATION_BLAST = ITEMS.register("wand_levitation_blast", () -> new Wand(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            durability(150)));
    public static final RegistryObject<Item> WAND_SUMMON_DEMISED = ITEMS.register("wand_summon_demised", () -> new Wand(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            durability(150)));
    public static final RegistryObject<Item> WAND_HANDLE = ITEMS.register("wand_handle", () -> new Item(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            stacksTo(16)));

    //SPELL SCROLLS

    public static final RegistryObject<Item> ICE_BOLT_SPELL_SCROLL = ITEMS.register("ice_bolt_spell_scroll", () -> new IceBoltSpellScroll(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            stacksTo(1)));
    public static final RegistryObject<Item> LEVITATION_BLAST_SPELL_SCROLL = ITEMS.register("levitation_blast_spell_scroll", () -> new LevitationBlastSpellScroll(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            stacksTo(1)));
    public static final RegistryObject<Item> SUMMON_DEMISED_SPELL_SCROLL = ITEMS.register("summon_demised_spell_scroll", () -> new SummonDemisedSpellScroll(new Item.Properties().tab(ModAbrakadavraTab.ABRAKADAVRA_TAB).
            stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
