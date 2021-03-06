package net.lomeli.cb.core.handler;

import net.lomeli.cb.lib.Strings;

import net.minecraft.util.Icon;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IconHandler {
    public static Icon[] fluidIcons = new Icon[5];

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void registerIcons(TextureStitchEvent.Pre event) {
        if (event.map.textureType != 0 && event.map.textureType == 1) {
            for (int i = 0; i < fluidIcons.length; i++) {
                fluidIcons[i] = event.map.registerIcon(Strings.MOD_ID.toLowerCase() + ":fluids/liquidCrystal_" + i);
            }
        }
    }

}
