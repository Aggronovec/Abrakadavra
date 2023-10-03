package net.monke.abrakadavra.client.render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.monke.abrakadavra.Abrakadavra;
import net.monke.abrakadavra.entity.FireBoltEntity;
import net.monke.abrakadavra.entity.LevitationBallEntity;

public class LevitationBallRendered extends ArrowRenderer<LevitationBallEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Abrakadavra.MOD_ID, "textures/entity/fire_bolt_projectile.png");
    public LevitationBallRendered(EntityRendererProvider.Context manager) {
        super(manager);
    }
    public ResourceLocation getTextureLocation(LevitationBallEntity arrow) {
        return TEXTURE;
    }
}