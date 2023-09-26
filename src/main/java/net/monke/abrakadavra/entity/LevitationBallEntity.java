package net.monke.abrakadavra.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.monke.abrakadavra.util.SpellsUtil;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;

import javax.annotation.Nullable;
import java.util.Optional;

public class LevitationBallEntity extends SpellAbstractProjectile {
    public LevitationBallEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public LevitationBallEntity(Level level, LivingEntity shooter) {
        this(EntityInit.LEVITATION_BALL.get(), level);
        setOwner(shooter);
    }

    @Override
    public void trailParticles() {
        Vec3 vec3 = getDeltaMovement();
        double d0 = this.getX() - vec3.x;
        double d1 = this.getY() - vec3.y;
        double d2 = this.getZ() - vec3.z;
        for (int i = 0; i < 4; i++) {
            Vec3 random = SpellsUtil.getRandomVec3(.2);
            this.level.addParticle(ParticleTypes.SMOKE, d0 - random.x, d1 + 0.5D - random.y, d2 - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
        }
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        SpellsUtil.spawnParticles(level, ParticleTypes.LAVA, x, y, z, 30, 1.5, .1, 1.5, 1, false);
    }

    @Override
    public boolean respectsGravity() {
        return true;
    }

    @Override
    public float getSpeed() {
        return .65f;
    }

    @Override
    protected void onHit(HitResult hitresult) {
        super.onHit(hitresult);
//        createLevitationArea(hitresult.getLocation());
        float explosionRadius = getExplosionRadius();
        var entities = level.getEntities(this, this.getBoundingBox().inflate(explosionRadius));
        for (Entity entity : entities) {
            double distance = entity.distanceToSqr(hitresult.getLocation());
            if (distance < explosionRadius * explosionRadius && canHitEntity(entity)) {
                if (SpellsUtil.hasLineOfSight(level, hitresult.getLocation(), entity.position().add(0, entity.getEyeHeight() * .5f, 0))) {
                    double p = (1 - Math.pow(Math.sqrt(distance) / (explosionRadius), 3));
                    float damage = (float) (this.damage * p);
                    applyDamage(entity, damage, indirectDamageSource(this, getOwner()));
                }
            }
        }
        discard();
    }

    public DamageSource indirectDamageSource(Entity projectile, @Nullable Entity attacker) {
        return new IndirectEntityDamageSource("air_damage", projectile, attacker);
    }

//    public void createLevitationArea(Vec3 location) {
//        if (!level.isClientSide) {
//            FireField fire = new FireField(level);
//            fire.setOwner(getOwner());
//            fire.setDuration(200);
//            fire.setDamage(damage / 5);
//            fire.setRadius(getExplosionRadius());
//            fire.setCircular();
//            fire.moveTo(location);
//            level.addFreshEntity(fire);
//        }
//    }

    @Override
    protected void doImpactSound(SoundEvent sound) {
        level.playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 2, 1.2f + level.random.nextFloat() * .2f);
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.of(SoundEvents.GENERIC_EXPLODE);
    }
    public static boolean applyDamage(Entity target, float baseAmount, DamageSource damageSource) {
        if (target instanceof LivingEntity livingTarget) {

            float adjustedDamage = baseAmount;
            boolean fromSummon = false;

            if (damageSource.getEntity() instanceof LivingEntity livingAttacker) {
                if (isFriendlyFireBetween(livingAttacker, livingTarget))
                    return false;
                livingAttacker.setLastHurtMob(target);
            }
            var flag = livingTarget.hurt(damageSource, adjustedDamage);
            if (fromSummon)
                livingTarget.setLastHurtByMob((LivingEntity) damageSource.getDirectEntity());
            return flag;
        } else {
            return target.hurt(damageSource, baseAmount);
        }

    }
    public static boolean isFriendlyFireBetween(Entity attacker, Entity target) {
        if (attacker == null || target == null)
            return false;
        var team = attacker.getTeam();
        if (team != null) {
            return team.isAlliedTo(target.getTeam()) && !team.isAllowFriendlyFire();
        }
        return false;
    }
}