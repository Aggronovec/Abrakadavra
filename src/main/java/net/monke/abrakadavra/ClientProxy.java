package net.monke.abrakadavra;


import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.monke.abrakadavra.client.gui.GUISorcerersManual;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Abrakadavra.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public void openBookGUI(ItemStack pItemStack) {
        Minecraft.getInstance().setScreen(new GUISorcerersManual(pItemStack));
    }

    public void openBookGUI(ItemStack pItemStack, String page) {
        Minecraft.getInstance().setScreen(new GUISorcerersManual(pItemStack, page));
    }

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }
}
