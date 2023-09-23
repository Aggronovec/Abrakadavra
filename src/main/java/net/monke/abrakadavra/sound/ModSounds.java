package net.monke.abrakadavra.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.monke.abrakadavra.Abrakadavra;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Abrakadavra.MOD_ID);

    public static RegistryObject<SoundEvent> PLACE_WAND
            = registerSoundEvents("place_wand");
    public static RegistryObject<SoundEvent> RUNE_TABLE
            = registerSoundEvents("rune_table");

    public static RegistryObject<SoundEvent> ICE_BOLT_CAST
            = registerSoundEvents("ice_bolt_cast");
    public static RegistryObject<SoundEvent> FIRE_BOLT_CAST
            = registerSoundEvents("fire_bolt_cast");
    public static RegistryObject<SoundEvent> ICE_BOLT_IMPACT
            = registerSoundEvents("ice_bolt_impact");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(Abrakadavra.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> new SoundEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
