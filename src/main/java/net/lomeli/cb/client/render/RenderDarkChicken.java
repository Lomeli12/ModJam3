package net.lomeli.cb.client.render;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.cb.lib.Strings;

@SideOnly(Side.CLIENT)
public class RenderDarkChicken extends RenderLiving{

    public RenderDarkChicken() {
        super(new ModelChicken(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity){
        return new ResourceLocation(Strings.MOD_ID.toLowerCase(), "textures/model/darkchicken.png");
    }

}
