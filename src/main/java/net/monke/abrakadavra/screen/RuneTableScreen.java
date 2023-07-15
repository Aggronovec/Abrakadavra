package net.monke.abrakadavra.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.monke.abrakadavra.Abrakadavra;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.monke.abrakadavra.block.custom.RuneTable;
import net.monke.abrakadavra.item.function.LevitationBlastSpellScroll;
import net.monke.abrakadavra.item.function.SummonDemisedSpellScroll;

public class RuneTableScreen extends AbstractContainerScreen<RuneTableMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Abrakadavra.MOD_ID, "textures/gui/rune_table_gui.png");
//    public String LEARNED_SPELLS_KEY = "LearnedSpells";
    public RuneTableScreen(RuneTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
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

}