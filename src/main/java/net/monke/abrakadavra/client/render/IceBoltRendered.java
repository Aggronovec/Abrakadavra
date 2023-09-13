package net.monke.abrakadavra.client.render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.monke.abrakadavra.Abrakadavra;
import net.monke.abrakadavra.entity.IceBoltEntity;

public class IceBoltRendered extends ArrowRenderer<IceBoltEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Abrakadavra.MOD_ID, "textures/entity/ice_bolt_projectile.png");

    public IceBoltRendered(EntityRendererProvider.Context manager) {
        super(manager);
    }
    public ResourceLocation getTextureLocation(IceBoltEntity arrow) {
        return TEXTURE;
    }

    @Override
    public Vec3 getRenderOffset(IceBoltEntity entity, float partialTicks) {
        // You can use this method to adjust the rendering offset of your entity.
        // For example, to raise the rendering position slightly:
        double yOffset = 0.2; // Adjust as needed
        return new Vec3(0, yOffset, 0);
    }
}