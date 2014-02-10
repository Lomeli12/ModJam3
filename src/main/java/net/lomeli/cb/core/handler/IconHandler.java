package net.lomeli.cb.core.handler;

import net.lomeli.cb.lib.Strings;

import net.minecraft.util.IIcon;

import net.minecraftforge.client.event.TextureStitchEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IconHandler {
    public static IIcon[] fluidIcons = new IIcon[5];

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() != 0 && event.map.getTextureType() == 1) {
            for (int i = 0; i < fluidIcons.length; i++) {
                fluidIcons[i] = event.map.registerIcon(Strings.MOD_ID.toLowerCase() + ":fluids/liquidCrystal_" + i);
            }
        }
    }

}
