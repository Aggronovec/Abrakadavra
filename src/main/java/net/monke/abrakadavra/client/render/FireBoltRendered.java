package net.monke.abrakadavra.client.render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.monke.abrakadavra.Abrakadavra;
import net.monke.abrakadavra.entity.FireBoltEntity;
import net.monke.abrakadavra.entity.IceBoltEntity;

public class FireBoltRendered extends ArrowRenderer<FireBoltEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Abrakadavra.MOD_ID, "textures/entity/ice_bolt_projectile.png");

    public FireBoltRendered(EntityRendererProvider.Context manager) {
        super(manager);
    }
    public ResourceLocation getTextureLocation(FireBoltEntity arrow) {
        return TEXTURE;
    }
}