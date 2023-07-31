package net.monke.abrakadavra.entity.spells;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

import static net.minecraft.core.BlockPos.getX;
import static net.minecraft.core.BlockPos.getY;
import static org.apache.commons.compress.compressors.CompressorStreamFactory.getZ;

public abstract class AbstractSpellProjectile {

    protected static final int EXPIRE_TIME = 15 * 20;
    protected int age;
    protected float damage;
    protected float explosionRadius;

    /**
     * Client Side, called every tick
     */
    public abstract void trailParticles();
    /**
     * Server Side, called alongside onHit()
     */

    public abstract void impactParticles(double x, double y, double z);

    public abstract float getSpeed();

    public abstract Optional<SoundEvent> getImpactSound();

    public AbstractSpellProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void shoot(Vec3 rotation) {
        setDeltaMovement(rotation.scale(getSpeed()));
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public void setExplosionRadius(float explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    public boolean respectsGravity() {
        return false;
    }

    @Override
    protected void onHit(HitResult hitresult) {
//        if (hitresult instanceof EntityHitResult entityHitResult)
//            if (entityHitResult.getEntity().getType() == EntityType.ENDERMAN || (entityHitResult.getEntity() instanceof LivingEntity livingEntity && PlayerMagicData.getPlayerMagicData(livingEntity).getSyncedData().hasDodgeEffect()))
//                return;
        super.onHit(hitresult);
        double x = xOld;
        double y = yOld;
        double z = zOld;

        if (!level.isClientSide) {
            impactParticles(x, y, z);
            getImpactSound().ifPresent(this::doImpactSound);
        }
    }

    protected void doImpactSound(SoundEvent sound) {
        level.playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 2, .9f + level.random.nextFloat() * .2f);
    }

    @Override
    protected void defineSynchedData() {

    }
    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("Damage", this.getDamage());
        pCompound.putFloat("ExplosionRadius", explosionRadius);


    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.damage = pCompound.getFloat("Damage");
        this.explosionRadius = pCompound.getFloat("ExplosionRadius");

    }

    @Override
    public boolean isOnFire() {
        return false;
    }

}
