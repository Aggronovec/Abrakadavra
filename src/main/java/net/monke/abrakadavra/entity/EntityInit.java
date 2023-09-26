package net.monke.abrakadavra.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.monke.abrakadavra.Abrakadavra;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Abrakadavra.MOD_ID);

    public static final RegistryObject<EntityType<IceBoltEntity>> ICE_BOLT_PROJECTILE = ENTITY_TYPES.register("ice_bolt",
            () -> EntityType.Builder.of((EntityType.EntityFactory<IceBoltEntity>) IceBoltEntity::new, MobCategory.MISC).sized(0.65F, 0.65F).build("ice_bolt"));
    public static final RegistryObject<EntityType<LevitationBallEntity>> LEVITATION_BALL = ENTITY_TYPES.register("levitation_ball",
            () -> EntityType.Builder.of((EntityType.EntityFactory<LevitationBallEntity>) LevitationBallEntity::new, MobCategory.MISC)
                    .sized(0.65F, 0.55F).clientTrackingRange(64).build(new ResourceLocation(Abrakadavra.MOD_ID, "levitation_ball").toString()));
    public static final RegistryObject<EntityType<FireBoltEntity>> FIRE_BOLT_PROJECTILE = ENTITY_TYPES.register("fire_bolt",
            () -> EntityType.Builder.of((EntityType.EntityFactory<FireBoltEntity>) FireBoltEntity::new, MobCategory.MISC).sized(0.1F, 0.1F).build("fire_bolt"));
}

