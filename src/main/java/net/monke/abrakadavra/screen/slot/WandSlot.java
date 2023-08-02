package net.monke.abrakadavra.screen.slot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.monke.abrakadavra.item.ModItems;
import net.monke.abrakadavra.screen.RuneTableScreen;
import org.jetbrains.annotations.NotNull;

public class WandSlot extends SlotItemHandler {
    public WandSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }
    @Override
    public boolean mayPlace(ItemStack stack) {

        return stack.getItem() == ModItems.WAND.get() ||
         stack.getItem() == ModItems.WAND_ICE_BOLT.get() ||
         stack.getItem() == ModItems.WAND_LEVITATION_BLAST.get() ||
         stack.getItem() == ModItems.WAND_SUMMON_DEMISED.get();
    }
    // Method to swap the wand for the desired one
    public void swapWand(ItemStack newWand) {
        // Get the item handler of the slot
        IItemHandler itemHandler = getItemHandler();

        // Check if the newWand is a valid wand item
        if (!mayPlace(newWand)) {
            return; // If not, don't perform the swap
        }

        // Check if the slot already has a wand
        ItemStack currentWand = itemHandler.getStackInSlot(getSlotIndex());
        if (currentWand.isEmpty()) {
            return; // If the slot is empty, don't perform the swap
        }

        // Swap the wands
        itemHandler.extractItem(getSlotIndex(), 1, false); // Remove the current wand
        itemHandler.insertItem(getSlotIndex(), newWand.copy(), false); // Insert the new wand
    }

}