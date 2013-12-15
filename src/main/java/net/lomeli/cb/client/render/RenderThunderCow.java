package net.lomeli.cb.client.render;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import net.lomeli.cb.lib.Strings;

public class RenderThunderCow extends RenderLiving{

    public RenderThunderCow() {
        super(new ModelCow(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(Strings.MOD_ID.toLowerCase(), "textures/model/thundercow.png");
    }

}
