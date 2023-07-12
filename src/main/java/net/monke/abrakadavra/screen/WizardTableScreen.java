package net.monke.abrakadavra.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.PlayerWallHeadBlock;
import net.monke.abrakadavra.Abrakadavra;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WizardTableScreen extends AbstractContainerScreen<WizardTableMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/wizard_table_gui.png");
//    public String LEARNED_SPELLS_KEY = "LearnedSpells";

    public WizardTableScreen(WizardTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

//    public boolean KnowsLevitationBlast () {
//        WizardTableMenu menu = getMenu();
//        Player pPlayer = menu.getPlayer();
//        CompoundTag playerData = pPlayer.getPersistentData();
//        if (!playerData.contains(LEARNED_SPELLS_KEY)) {
//            playerData.put(LEARNED_SPELLS_KEY, new CompoundTag());
//        }
//        CompoundTag persistentData = playerData.getCompound(LEARNED_SPELLS_KEY);
//        if (persistentData.contains("Levitation Blast")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - 230) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x + 27, y, 0, 0, 230, imageHeight);
//        if(KnowsLevitationBlast()) {
//            this.blit(pPoseStack, x + 84, y + 22, 2, 152, 26, 36);
//        }
    }
    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}