package net.monke.abrakadavra.screen.slot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.monke.abrakadavra.Abrakadavra;

public class SpellSlot extends SlotItemHandler {
    public SpellSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }
    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

}