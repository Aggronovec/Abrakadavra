package net.monke.abrakadavra.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.monke.abrakadavra.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

public class IceBoltEntity extends AbstractArrow {
    public void spawnCastingParticles() {
        // Add particle effects here when the spell is cast.
        double x = this.getX();
        double y = this.getY()+0.5D;
        double z = this.getZ();
        this.level.addParticle(ParticleTypes.SNOWFLAKE, x, y, z, 0, 0.08 + level.random.nextDouble(0.8), 0);
        // You can customize the particle type and parameters as needed.
    }

    private static final double MAX_DISTANCE = 100.0; // Maximum travel distance in blocks
    private static final double PROJECTILE_SPEED = 1.8; // Adjust the speed as needed
    private double distanceTraveled = 0.0; // Track the distance traveled
    public IceBoltEntity(EntityType<IceBoltEntity> entityType, Level world) {
        super(entityType, world);}
//    public IceBoltEntity(EntityType<IceBoltEntity> entityType, double x, double y, double z, Level world) {
//        super(entityType, x, y, z, world); }

    public IceBoltEntity(EntityType<IceBoltEntity> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world);
        // Disable gravity for the projectile
        this.setNoGravity(true);
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ICE_BOLT_CAST.get(), SoundSource.PLAYERS,
                level.random.nextFloat() * 0.05F + 0.19F, level.random.nextFloat() * 0.6F + 0.9F);
    }
    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }
    @Override
    protected void onHitEntity(EntityHitResult ray) {
//        super.onHitEntity(ray);
        this.setBaseDamage(0);
        Entity target = ray.getEntity();
        // Check if the hit entity is a LivingEntity (e.g., a player or a mob)
        if (target instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) target;
            // Apply your custom freeze effect to the target for 15 seconds (20 ticks per second)
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15 * 20, 0)); // Adjust the duration and amplifier as needed
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ICE_BOLT_IMPACT.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        // Dispose of the projectile
        this.discard();
        // this, x, y, z, explosionStrength, setsFires, breakMode
 //       this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.BREAK);
    }
    @Override
    protected void onHitBlock(BlockHitResult ray) {
//        super.onHitBlock(ray);
        BlockState theBlockYouHit = this.level.getBlockState(ray.getBlockPos());
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ICE_BOLT_IMPACT.get(), SoundSource.PLAYERS,
                level.random.nextFloat() * 0.5F + 0.9F, level.random.nextFloat() * 0.1F + 0.9F);
        this.discard();
    }
    @Override
    protected void tickDespawn() {
//        if (this.inGroundTime > 60){
//            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.BREAK);
//            this.discard();
//        }
        if (this.distanceTraveled >= MAX_DISTANCE) {
            // Dispose of the projectile if it reaches the maximum distance
            this.discard(); }
    }
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    @Override
    public void tick() {
        // Update the entity's position based on the direction to the crosshair position
        if (this.tickCount == 1 && this.getOwner() instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) this.getOwner();
            Vec3 crosshairPos = serverPlayer.getLookAngle();
            Vec3 motion = crosshairPos.normalize().scale(PROJECTILE_SPEED); // Adjust the scale as needed
            this.setDeltaMovement(motion.x, motion.y, motion.z);
        }

        // Spawn particles along the trajectory
        if (this.level.isClientSide()) {
            // Calculate the number of particles based on the distance traveled
            int numParticles = (int) this.distanceTraveled / 5; // Adjust the interval as needed

            for (int i = 0; i < numParticles; i++) {
                // Calculate the position for the particle
                double particleX = this.getX() + this.getDeltaMovement().x * i;
                double particleY = this.getY() + this.getDeltaMovement().y * i;
                double particleZ = this.getZ() + this.getDeltaMovement().z * i;

                // Spawn a snowflake particle at the calculated position
                this.level.addParticle(ParticleTypes.SNOWFLAKE, particleX, particleY, particleZ, 0, 0.08 + this.level.random.nextDouble(0.8), 0);
            }
        }
        // Update the distance traveled
        this.distanceTraveled += this.getDeltaMovement().length();

        // Call super.tick() for other functionality
        super.tick();
    }

    @Override
    public void setBaseDamage(double pDamage) {
        pDamage = 0;
        super.setBaseDamage(pDamage);
    }
    @Override
    protected AABB makeBoundingBox() {

        return super.makeBoundingBox().inflate(2);
    }

}