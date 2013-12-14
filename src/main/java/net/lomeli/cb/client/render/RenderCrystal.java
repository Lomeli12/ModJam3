package net.lomeli.cb.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.lomeli.cb.client.model.ModelCrystal;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystal;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCrystal extends TileEntitySpecialRenderer {
    private ModelCrystal model;

    public RenderCrystal() {
        model = new ModelCrystal();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
        renderCrystal((TileCrystal) tileentity, d0, d1, d2, f);
    }

    public void renderCrystal(TileCrystal tile, double d0, double d1, double d2, float f) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Strings.MOD_ID.toLowerCase(),
                "textures/model/Crystal.png"));

        GL11.glPushMatrix();

        GL11.glTranslatef((float) d0 + 0.5F, (float) d1 + 1F, (float) d2 + 0.5F);

        GL11.glScaled(1.5d, -1.5d, 1.5d);

        GL11.glColor3d(255, 0, 0);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if(tile.isNatural())
            GL11.glColor4f(73F, 12F, 84F, 0.7F);
        else
            GL11.glColor4f(tile.red, tile.green, tile.blue, 0.65F);

        model.render(0.0625F);

        GL11.glDisable(GL11.GL_BLEND);
        if(tile.isNatural())
            GL11.glColor4f(73F, 12F, 84F, 1F);
        else
            GL11.glColor4f(tile.red, tile.green, tile.blue, 1F);
        //GL11.glColor4f(tile.red, tile.green, tile.blue, 1.0F);

        GL11.glPopMatrix();
    }

}
