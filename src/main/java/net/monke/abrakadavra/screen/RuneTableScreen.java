package net.monke.abrakadavra.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
import net.monke.abrakadavra.item.ModItems;
import net.monke.abrakadavra.item.function.Wand;
import net.monke.abrakadavra.networking.ModMessages;
import net.monke.abrakadavra.networking.packet.*;
import net.monke.abrakadavra.screen.slot.SpellSlot;
import net.monke.abrakadavra.screen.slot.WandSlot;


public class RuneTableScreen extends AbstractContainerScreen<RuneTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/rune_table_gui.png");
    public RuneTableScreen(RuneTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.runeTableMenu = pMenu; }
    private int selectedSlot = 0;
    private ItemStack previousWandStack = ItemStack.EMPTY;
    private boolean insertedWandSlot = true;
    public static boolean disableSlots = false;
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
            // Here, we assume the first slot is index 0, second slot index 1, and so on.
            // Render the highlight texture here, you can use blit or any other method as needed
            setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/rune_table_gui.png"));
            this.blit(pPoseStack, x - 1, y - 1, 31, 169, 18, 18);
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

        if (runeTableMenu.getSlot(36).hasItem()) {
//            ModMessages.sendToServer(new RuneTablePacket());
            ItemStack wandStack = runeTableMenu.getSlot(36).getItem();
            if (wandStack.getItem() == ModItems.WAND.get()) {
                // Handle the wand highlight here
            } else if (wandStack.getItem() == ModItems.WAND_ICE_BOLT.get()) {
                selectedSlot = 37;
//                renderSlotHighlight(pPoseStack, x + 205, y + 88 + 19 * 0); // First slot
            } else if (wandStack.getItem() == ModItems.WAND_LEVITATION_BLAST.get()) {
                selectedSlot = 38;
//                renderSlotHighlight(pPoseStack, x + 205, y + 88 + 19 * 1); // Second slot
            } else if (wandStack.getItem() == ModItems.WAND_SUMMON_DEMISED.get()) {
                selectedSlot = 39;
//                renderSlotHighlight(pPoseStack, x + 205, y + 88 + 19 * 2); // Third slot
            }
            switch (selectedSlot) { // method to render the slot highlight for the particular slot
                case 37:
                    renderSlotHighlight(pPoseStack, x + 205, y + 88 + 19 * 0); // First slot
                    break;
                case 38:
                    renderSlotHighlight(pPoseStack, x + 205, y + 88 + 19 * 1); // Second slot
                    break;
                case 39:
                    renderSlotHighlight(pPoseStack, x + 205, y + 88 + 19 * 2); // Third slot
                    break;
            }

            if (CheckSpellPacket.HasIceBoltSpell) {
                setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/ice_bolt.png"));
                this.blit(pPoseStack, x + 205, y + 88, 0, 0, 16, 16, 16, 16);
            } else {
                setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/ice_bolt_locked.png"));
                this.blit(pPoseStack, x + 205, y + 88, 0, 0, 16, 16, 16, 16);
            }

            if (CheckSpellPacket.HasLevitationBlastspell) {
                setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/levitation_blast.png"));
                this.blit(pPoseStack, x + 205, y + 107, 0, 0, 16, 16, 16, 16);
            } else {
                setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/levitation_blast_locked.png"));
                this.blit(pPoseStack, x + 205, y + 107, 0, 0, 16, 16, 16, 16);
            }

            if (CheckSpellPacket.HasSummonDemisedSpell) {
                setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/summon_demised.png"));
                this.blit(pPoseStack, x + 205, y + 126, 0, 0, 16, 16, 16, 16);
            } else {
                setTexture(new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/spell_icons/summon_demised_locked.png"));
                this.blit(pPoseStack, x + 205, y + 126, 0, 0, 16, 16, 16, 16);
            }
        }
        else {
            selectedSlot = -1;
        }
    }
    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    @Override
    public void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
        Player player = Minecraft.getInstance().player;
        // Check if the clicked slot is a spell slot and the wand is present in the WandSlot
        if (pSlot instanceof SpellSlot && pMouseButton == 0 && runeTableMenu.getSlot(36).hasItem()) {
            ItemStack wandStack = runeTableMenu.getSlot(36).getItem();
            // Determine the spell name based on the slotId
            switch (pSlotId) {
                case 37:
                    if (CheckSpellPacket.HasIceBoltSpell) {
                        player.displayClientMessage(new TranslatableComponent("Ice Bolt was infused!"), true);
//                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
//                                new TranslatableComponent("Ice Bolt was infused!"), new TranslatableComponent("")));
                        Wand.setSpellAssignedToWand(wandStack, "Ice Bolt");
                        selectedSlot = pSlotId;
                        ModMessages.sendToServer(new IceBoltPacket());
                    } else {
                        player.displayClientMessage(new TranslatableComponent("You don't yet know Ice Bolt spell!"), true);
//                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
//                                new TranslatableComponent("You don't yet know Ice Bolt spell!"), new TranslatableComponent("")));
                                }
                    break;
                case 38:
                    if (CheckSpellPacket.HasLevitationBlastspell) {
                        player.displayClientMessage(new TranslatableComponent("Levitation Blast was infused!"), true);
//                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
//                                new TranslatableComponent("Levitation Blast was infused!"), new TranslatableComponent("")));
                        Wand.setSpellAssignedToWand(wandStack, "Levitation Blast");
                        selectedSlot = pSlotId;
                        ModMessages.sendToServer(new LevitationPacket());
                    } else {
                        player.displayClientMessage(new TranslatableComponent("You don't yet know Levitation Blast spell!"), true);
//                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
//                            new TranslatableComponent("You don't yet know Levitation Blast spell!"), new TranslatableComponent("")));
                            }
                    break;
                case 39:
                    if (CheckSpellPacket.HasSummonDemisedSpell) {
                        player.displayClientMessage(new TranslatableComponent("Summon Demised was infused!"), true);
//                        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
//                                new TranslatableComponent("Summon Demised was infused!"), new TranslatableComponent("")));
                        Wand.setSpellAssignedToWand(wandStack, "Summon Demised");
                        selectedSlot = pSlotId;
                        ModMessages.sendToServer(new SummonDemisedPacket());
                    } else {
                    player.displayClientMessage(new TranslatableComponent("You don't yet know Summon Demised spell!"), true);
//                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
//                            new TranslatableComponent("You don't yet know Summon Demised spell!"), new TranslatableComponent("")));
                            }
                    break;
            }
        }
        super.slotClicked(pSlot, pSlotId, pMouseButton, pType);
    }
    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
//        super.renderLabels(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    if (runeTableMenu.getSlot(36).hasItem()) {
        if (!insertedWandSlot) {
            ModMessages.sendToServer(new PlaceWandPacket());
            insertedWandSlot = true;
        }
    } else {
        insertedWandSlot = false;
    }
//        ItemStack currentWandStack = runeTableMenu.getSlot(36).getItem();
//        if (!ItemStack.matches(currentWandStack, previousWandStack)) {
//            previousWandStack = currentWandStack;
            // Check if the wand has been inserted into the slot
//            if (!currentWandStack.isEmpty()) {
//                ModMessages.sendToServer(new PlaceWandPacket());
//            }
//        }
    }
}