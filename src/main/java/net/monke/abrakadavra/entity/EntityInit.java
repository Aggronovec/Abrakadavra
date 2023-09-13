package net.monke.abrakadavra.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.monke.abrakadavra.Abrakadavra;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Abrakadavra.MOD_ID);

    public static final RegistryObject<EntityType<IceBoltEntity>> ICE_BOLT_PROJECTILE = ENTITY_TYPES.register("ice_bolt",
            () -> EntityType.Builder.of((EntityType.EntityFactory<IceBoltEntity>) IceBoltEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("ice_bolt"));
}

