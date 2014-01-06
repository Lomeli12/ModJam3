package net.lomeli.cb.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.IItemRenderer;

import net.lomeli.cb.client.model.ModelCrystal;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystal;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCrystal extends TileEntitySpecialRenderer implements IItemRenderer {
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

        GL11.glScaled(1d, -1d, 1d);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(tile.red / 255F, tile.green / 255F, tile.blue / 255F, 0.7F);

        model.render(0.0625F);

        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        renderCrystalItem(new TileCrystal(), 0.1D, 0.1D, 0.1D, 0.1F);
    }

    public void renderCrystalItem(TileCrystal tile, double d0, double d1, double d2, float f) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Strings.MOD_ID.toLowerCase(),
                "textures/model/Crystal.png"));

        GL11.glPushMatrix();

        GL11.glTranslatef((float) d0 + 0.5F, (float) d1 + 1F, (float) d2 + 0.5F);

        GL11.glScaled(1d, -1d, 1d);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(18 / 255F, 200 / 255F, 240 / 255F, 0.7F);

        model.render(0.0625F);

        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }
}
