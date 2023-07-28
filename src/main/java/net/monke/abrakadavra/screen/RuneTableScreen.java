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
    private static final ResourceLocation TEXTURE = new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/rune_table_gui.png");
    public RuneTableScreen(RuneTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.runeTableMenu = pMenu; }
    private int selectedSlot = 37;
    private RuneTableMenu runeTableMenu;
    @Override
    protected void init() {
        super.init();
    }
    private void setTexture(ResourceLocation texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
    }
    private void renderSlotHighlight(PoseStack pPoseStack, int x, int y) {
        if (selectedSlot >= 37 && selectedSlot <= 39) {
            // Check if the selected slot is within the spell slot range and matches the current y position
            // You can modify the x and y positions as needed to adjust the highlight position
            // For example, x + 205, y + 88 is the position of the first slot
            // Each slot is 18 pixels apart in the y-axis, so we use (y - 88) / 18 to calculate the slot index
            // Here, we assume the first slot is index 0, second slot index 1, and so on.
            // Render the highlight texture here, you can use blit or any other method as needed
            // For example, to draw a red rectangle highlight, you can use:
            setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/rune_table_gui.png"));
            this.blit(pPoseStack, x - 2, y - 2, 32, 169, 20, 20);
//            fill(pPoseStack, x - 1, y - 1, x + 18, y + 18, 0xFFFF0000); // Red color (Alpha, Red, Green, Blue)
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - 230) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x + 27, y, 0, 0, 230, imageHeight);

        switch (selectedSlot) { // method to render the slot highlight for the particular slot
            case 37:
                renderSlotHighlight(pPoseStack, x + 205, y + 88 + 19 * 0); // First slot
                break;
            case 38:
                renderSlotHighlight(pPoseStack, x + 205, y + 88 + 19 * 1); // Second slot
                break;
            case 39:
                renderSlotHighlight(pPoseStack, x + 205, y + 88 + 19 * 2); // Third slot
                break; }

        if (RuneTable.HasIceBoltSpell) {
            setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/ice_bolt.png"));
            this.blit(pPoseStack, x + 205, y + 88, 0, 0, 16, 16, 16, 16);
        } else {
            setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/ice_bolt_locked.png"));
            this.blit(pPoseStack, x + 205, y + 88, 0, 0, 16, 16, 16, 16);
        }

        if (RuneTable.HasLevitationBlastspell) {
            setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/levitation_blast.png"));
            this.blit(pPoseStack, x + 205, y + 107, 0, 0, 16, 16, 16, 16);
        } else {
            setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/levitation_blast_locked.png"));
            this.blit(pPoseStack, x + 205, y + 107, 0, 0, 16, 16, 16, 16);
        }

        if (RuneTable.HasSummonDemisedSpell) {
            setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/summon_demised.png"));
            this.blit(pPoseStack, x + 205, y + 126, 0, 0, 16, 16, 16, 16);
        } else {
            setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/summon_demised_locked.png"));
            this.blit(pPoseStack, x + 205, y + 126, 0, 0, 16, 16, 16, 16);
        }
    }
    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    @Override
    public void onClose() {
        ItemStack wandStack = runeTableMenu.getSlot(0).getItem();
        Wand.setSpellAssignedToWand(wandStack, "HEUREKA");
        super.onClose();
    }

    @Override
    public void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
        // Check if the clicked slot is a spell slot and the wand is present in the WandSlot
        if (pSlot instanceof SpellSlot && pMouseButton == 0 && runeTableMenu.getSlot(36).hasItem()) {
            ItemStack wandStack = runeTableMenu.getSlot(36).getItem();
            // Determine the spell name based on the slotId
            switch (pSlotId) {
                case 37:
                    if (RuneTable.HasIceBoltSpell) {
                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                                new TranslatableComponent("Ice Bolt was infused!"), new TranslatableComponent("")));
                        Wand.setSpellAssignedToWand(wandStack, "Ice Bolt");
                        selectedSlot = pSlotId;
                    } else {
                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                                new TranslatableComponent("You don't know that spell!"), new TranslatableComponent(""))); }
                    break;
                case 38:
                    if (RuneTable.HasLevitationBlastspell) {
                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                                new TranslatableComponent("Levitation Blast was infused!"), new TranslatableComponent("")));
                        Wand.setSpellAssignedToWand(wandStack, "Levitation Blast");
                        selectedSlot = pSlotId;
                    } else {
                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                            new TranslatableComponent("You don't know that spell!"), new TranslatableComponent(""))); }
                    break;
                case 39:
                    if (RuneTable.HasSummonDemisedSpell) {
                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                                new TranslatableComponent("Necromancery was infused!"), new TranslatableComponent("")));
                        Wand.setSpellAssignedToWand(wandStack, "Necromancery");
                        selectedSlot = pSlotId;
                    } else {
                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                            new TranslatableComponent("You don't know that spell!"), new TranslatableComponent(""))); }
                    break;
                // Add more cases for other slots if needed
            }
            // Play a sound or show a particle effect if desired
            // pPlayer.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);

            init(); // refreshing the screen by calling init() again
        }
        super.slotClicked(pSlot, pSlotId, pMouseButton, pType);
    }
}