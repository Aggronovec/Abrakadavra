package net.monke.abrakadavra.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.monke.abrakadavra.item.ModItems;

public class WandSlot extends SlotItemHandler {
    public WandSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {

        return stack.getItem() == ModItems.WAND.get();
    }
}