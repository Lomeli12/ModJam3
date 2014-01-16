package net.lomeli.cb.client.render;

import net.lomeli.cb.client.model.ModelFireWolf;
import net.lomeli.cb.lib.Strings;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFireWolf extends RenderLiving {

    public RenderFireWolf() {
        super(new ModelFireWolf(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return new ResourceLocation(Strings.MOD_ID.toLowerCase(), "textures/model/firewolf.png");
    }

}
