package net.monke.abrakadavra.entity;

import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.network.NetworkHooks;
import net.monke.abrakadavra.sound.ModSounds;

public class FireBoltEntity extends AbstractArrow {
    public void spawnCastingParticles() {
        // Add particle effects here when the spell is cast.
        double x = this.getX();
        double y = this.getY()+0.5D;
        double z = this.getZ();
        this.level.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, x, y, z, 0, 0.08 + level.random.nextDouble(0.1), 0);
        // You can customize the particle type and parameters as needed.
    }

    private static final double MAX_DISTANCE = 100.0; // Maximum travel distance in blocks
    private static final double PROJECTILE_SPEED = 1.8; // Adjust the speed as needed
    private double distanceTraveled = 0.0; // Track the distance traveled
    public FireBoltEntity(EntityType<FireBoltEntity> entityType, Level world) {
        super(entityType, world);
    }

    public FireBoltEntity(EntityType<FireBoltEntity> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world);
        // Disable gravity for the projectile
        this.setNoGravity(true);
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.FIRE_BOLT_CAST.get(), SoundSource.PLAYERS,
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
        // Dispose of the projectile
        this.discard();
        // this, x, y, z, explosionStrength, setsFires, breakMode
       this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3.0f, true, Explosion.BlockInteraction.BREAK);
    }

    @Override
    protected void onHitBlock(BlockHitResult ray) {
//        super.onHitBlock(ray);
        BlockState hitBlockState = this.level.getBlockState(ray.getBlockPos());
        Block hitBlock = hitBlockState.getBlock();
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2.0f, true, Explosion.BlockInteraction.BREAK);
//        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ICE_BOLT_IMPACT.get(), SoundSource.PLAYERS,
//                level.random.nextFloat() * 0.5F + 0.9F, level.random.nextFloat() * 0.1F + 0.9F);
            this.discard();
        }

    @Override
    protected void tickDespawn() {
        if (this.isInWater()) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.FIRE_BOLT_IN_WATER.get(), SoundSource.PLAYERS,
                    level.random.nextFloat() * 0.035F + 0.15F, level.random.nextFloat() * 0.6F + 0.9F);
            this.discard();
        }
//        if (this.inGroundTime > 60){
//            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.BREAK);
//            this.discard();
//        }
            this.discard();
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

                // Spawn a particle at the calculated position
                this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, particleX, particleY, particleZ, 0, 0.08 + this.level.random.nextDouble(0.8), 0);
                this.level.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, 0, 0.08 + this.level.random.nextDouble(0.8), 0);
            }
        }
        // Update the distance traveled
        this.distanceTraveled += this.getDeltaMovement().length();
        if (this.distanceTraveled >= MAX_DISTANCE) {
            // Dispose of the projectile if it reaches the maximum distance
            this.discard(); }

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