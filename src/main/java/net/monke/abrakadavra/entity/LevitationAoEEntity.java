package net.monke.abrakadavra.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkHooks;

public class LevitationAoEEntity extends Entity {

    private int duration;
    private int amplifier;
    private Lazy<LivingEntity> caster;
    public LevitationAoEEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    public LevitationAoEEntity(EntityType<?> type, Level world, LivingEntity caster, int duration, int amplifier) {
        this(type, world);
        this.caster = Lazy.of(() -> caster);
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    protected void defineSynchedData() {
        // Add any synchronized data here, if needed.
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        // Read entity data from NBT here.
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        // Write entity data to NBT here.
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return null; //NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide && this.caster != null && this.duration > 0) {
            // Find entities in the AoE
            double radius = 1.5; // Adjust the radius as needed
            for (LivingEntity entity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(radius))) {
                if (entity != this.caster.get()) {
                    // Apply levitation to entities within the AoE
                    entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, this.duration, this.amplifier));
                }
            }
            // Remove the AoE entity after applying the effect
            this.remove(RemovalReason.DISCARDED);
        }
    }
}
