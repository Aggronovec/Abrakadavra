package net.monke.abrakadavra.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.monke.abrakadavra.Abrakadavra;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.monke.abrakadavra.block.custom.RuneTable;
import net.monke.abrakadavra.item.function.Wand;
import net.monke.abrakadavra.screen.slot.SpellSlot;

public class RuneTableScreen extends AbstractContainerScreen<RuneTableMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/rune_table_gui.png");
//    public String LEARNED_SPELLS_KEY = "LearnedSpells";
    public RuneTableScreen(RuneTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.runeTableMenu = pMenu;
    }
    private RuneTableMenu runeTableMenu;

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - 230) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x + 27, y, 0, 0, 230, imageHeight);

        this.blit(pPoseStack, x + 204, y + 91, 1, 167, 13, 13); // Glowing of Ice spell
        if (RuneTable.HasLevitationBlastspell) {
            this.blit(pPoseStack, x + 219, y + 91, 15, 167, 13, 13); // Glowing of Levitation spell
        }
        if (RuneTable.HasSummonDemisedSpell) {
            this.blit(pPoseStack, x + 234, y + 91, 29, 167, 13, 13); // Glowing of Necromancery spell
        }
    }
    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
    @Override
    protected void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
        // Check if the clicked slot is a spell slot and the wand is present in the WandSlot
        if (pSlot instanceof SpellSlot && pMouseButton == 0 && runeTableMenu.getSlot(36).hasItem()) {
            ItemStack wandStack = runeTableMenu.getSlot(36).getItem();
            String spellName = "";
            // Determine the spell name based on the slotId
            switch (pSlotId) {
                case 37:
                    spellName = "Ice Bolt";
                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                            new TranslatableComponent("Ice Bolt was infused!"), new TranslatableComponent("")));
                    Wand.setSpellAssignedToWand(wandStack, spellName);
                    break;
                case 38:
                    if (RuneTable.HasLevitationBlastspell) {
                        spellName = "Levitation Blast";
                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                                new TranslatableComponent("Levitation Blast was infused!"), new TranslatableComponent("")));
                        Wand.setSpellAssignedToWand(wandStack, spellName);
                    } else {
                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                            new TranslatableComponent("You don't know that spell!"), new TranslatableComponent(""))); }
                    break;
                case 39:
                    if (RuneTable.HasSummonDemisedSpell) {
                        spellName = "Necromancery";
                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                                new TranslatableComponent("Necromancery was infused!"), new TranslatableComponent("")));
                        Wand.setSpellAssignedToWand(wandStack, spellName);
                    } else {
                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                            new TranslatableComponent("You don't know that spell!"), new TranslatableComponent(""))); }
                    break;
                // Add more cases for other slots if needed
            }
            // Insert the spell name into the wand's NBT data
            // Play a sound or show a particle effect if desired
            // pPlayer.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
            // Update the screen to refresh the tooltip with the new spell
        }
        super.slotClicked(pSlot, pSlotId, pMouseButton, pType);
    }
}