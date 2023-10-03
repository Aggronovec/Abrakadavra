package net.monke.abrakadavra.item.function;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.monke.abrakadavra.entity.EntityInit;
import net.monke.abrakadavra.entity.FireBoltEntity;
import net.monke.abrakadavra.entity.IceBoltEntity;
import net.monke.abrakadavra.entity.LevitationBallEntity;
import net.monke.abrakadavra.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wand extends Item {
    public Wand(Properties pProperties) {
        super(pProperties);
    }
    private static final String SPELL_NBT_KEY = "SpellAssignedToWand ";
    public static final String DEFAULT_SPELL = ""; // Default spell if no spell is assigned
    public static void setSpellAssignedToWand(ItemStack wandStack, String spellName) {
        CompoundTag nbtData = new CompoundTag();
//        CompoundTag wandTag = wandStack.getOrCreateTag();
        nbtData.putString(SPELL_NBT_KEY, spellName);
        wandStack.setTag(nbtData);
    }
    public static String getSpellAssignedToWand(ItemStack wandStack) {
        CompoundTag wandTag = wandStack.getTag();
        if (wandTag != null && wandTag.contains(SPELL_NBT_KEY)) {
            return wandTag.getString(SPELL_NBT_KEY);
        }
        return DEFAULT_SPELL;
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String spellName = getSpellAssignedToWand(pStack);
        switch (getRegistryName().toString()) {
            case "abrakadavra:wand":
                    pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand.tooltip")); // + .append(new TextComponent(spellName)
                    break;
            case "abrakadavra:wand_ice_bolt":
                    pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand_ice_bolt.tooltip")); // + .append(new TextComponent(spellName)
                    break;
            case "abrakadavra:wand_fire_bolt":
                    pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand_fire_bolt.tooltip")); // + .append(new TextComponent(spellName)
                    break;
            case "abrakadavra:wand_levitation_blast":
                    pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand_levitation_blast.tooltip")); // + .append(new TextComponent(spellName)
                    break;
            case "abrakadavra:wand_summon_demised":
                    pTooltipComponents.add(new TranslatableComponent("tooltip.abrakadavra.wand_summon_demised.tooltip")); // + .append(new TextComponent(spellName)
                    break;
        }
    }
    @Override
    public boolean isFoil(ItemStack pStack) {
        return  pStack.getItem() == ModItems.WAND_ICE_BOLT.get() ||
                pStack.getItem() == ModItems.WAND_FIRE_BOLT.get() ||
                pStack.getItem() == ModItems.WAND_LEVITATION_BLAST.get() ||
                pStack.getItem() == ModItems.WAND_SUMMON_DEMISED.get();
        //        return pStack.hasTag();
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 20;
    }
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack heldItem = pPlayer.getItemInHand(pUsedHand);
        // Get the player's look vector
        Vec3 lookVec = pPlayer.getLookAngle();

// Calculate the position for the particle
        double offsetX = 0.5 * lookVec.x; // 0.5 blocks in front
        double offsetY = pPlayer.getY() + 0.2D + pLevel.random.nextDouble(0.8);
        double offsetZ = 0.5 * lookVec.z; // 0.5 blocks in front
        if (!pPlayer.level.isClientSide()){

                if (heldItem.getItem() == ModItems.WAND_FIRE_BOLT.get()) {
                    FireBoltEntity arrow = new FireBoltEntity(EntityInit.FIRE_BOLT_PROJECTILE.get(), pPlayer, pPlayer.level);
                    arrow.setDeltaMovement(lookVec.x, lookVec.y, lookVec.z); // Use the player's look vector
                    pPlayer.level.addFreshEntity(arrow);
                }
                if (heldItem.getItem() == ModItems.WAND_LEVITATION_BLAST.get()) {
                    LevitationBallEntity arrow = new LevitationBallEntity(EntityInit.LEVITATION_BALL_PROJECTILE.get(), pPlayer, pPlayer.level);
                    arrow.setDeltaMovement(lookVec.x, lookVec.y, lookVec.z); // Use the player's look vector
                    pPlayer.level.addFreshEntity(arrow);
                }
                if (heldItem.getItem() == ModItems.WAND_ICE_BOLT.get()) {
                    IceBoltEntity arrow = new IceBoltEntity(EntityInit.ICE_BOLT_PROJECTILE.get(), pPlayer, pPlayer.level);
                    arrow.setDeltaMovement(lookVec.x, lookVec.y, lookVec.z); // Use the player's look vector
                    pPlayer.level.addFreshEntity(arrow);
                }
        } else {
            if (heldItem.getItem() == ModItems.WAND_FIRE_BOLT.get()) {
                for (int i = 0; i < 12; i++) {
                    pLevel.addParticle(ParticleTypes.FLAME, pPlayer.getX() + offsetX, offsetY, pPlayer.getZ() + offsetZ,
                            0d, 0.015d + pLevel.random.nextDouble(0.075d), 0d);
                }
            }
            if (heldItem.getItem() == ModItems.WAND_ICE_BOLT.get()) {
                for (int i = 0; i < 10; i++) {
                    pLevel.addParticle(ParticleTypes.SNOWFLAKE, pPlayer.getX(),
                            pPlayer.getY() + 0.2D + pLevel.random.nextDouble(0.8), pPlayer.getZ() + pLevel.random.nextDouble(0.6),
                            0d, 0.015d + pLevel.random.nextDouble(0.075d), 0d);
                }
            }
            if (heldItem.getItem() == ModItems.WAND_LEVITATION_BLAST.get()) {
                for (int i = 0; i < 10; i++) {
                    pLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pPlayer.getX(),
                            pPlayer.getY() + 0.2D + pLevel.random.nextDouble(0.8), pPlayer.getZ() + pLevel.random.nextDouble(0.6),
                            0d, 0.015d + pLevel.random.nextDouble(0.075d), 0d);
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}