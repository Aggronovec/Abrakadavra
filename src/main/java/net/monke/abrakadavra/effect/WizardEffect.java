package net.monke.abrakadavra.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class WizardEffect extends MobEffect {
    public WizardEffect(MobEffectCategory p_19451_, int p_19452_) {

        super(MobEffectCategory.NEUTRAL, 3124687);
        // Make the effect invisible in the player's inventory
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}