package net.lomeli.cb.core.handler;

import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.cb.lib.Strings;

@SideOnly(Side.CLIENT)
public class SoundHandler {
    private static SoundHandler instance;

    @SideOnly(Side.CLIENT)
    public static SoundHandler getSoundHandler() {
        if(instance == null)
            instance = new SoundHandler();
        return instance;
    }

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void registerMusic(SoundLoadEvent event) {
        try {
            event.manager.soundPoolStreaming.addSound(Strings.MOD_ID.toLowerCase() + ":chickenTechno.ogg");
        }catch(Exception e) {
        }
    }

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onPlayStreaming(PlayStreamingEvent event) {
        if(event.name == "chickenTechno")
            FMLClientHandler.instance().getClient().sndManager.playStreaming(Strings.MOD_ID.toLowerCase() + ":chickenTechno",
                    event.x + 0.5F, event.y + 0.5F, event.z + 0.5F);
    }
}
