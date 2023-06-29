package net.monke.abrakadavra.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModAbrakadavraTab {
    public static final CreativeModeTab ABRAKADAVRA_TAB = new CreativeModeTab("abrakadavratab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.WAND.get());
        }
    };
}
