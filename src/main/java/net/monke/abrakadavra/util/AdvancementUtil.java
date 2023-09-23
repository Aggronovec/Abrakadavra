package net.monke.abrakadavra.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class AdvancementUtil {
public static boolean TestForWizard (Player pPlayer, CompoundTag persistentData) {
    if (persistentData.contains("Fire Bolt")) {
        if (persistentData.contains("Summon Demised")) {
            if (persistentData.contains("Levitation Blast")) {
                return true;
            }
            return false;
        }
        return false;
    }
    return false;
}
}
