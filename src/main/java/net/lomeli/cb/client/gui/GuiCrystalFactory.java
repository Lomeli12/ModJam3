package net.lomeli.cb.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.lomeli.cb.inv.ContainerCrystalFactory;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import net.minecraftforge.fluids.FluidStack;

public class GuiCrystalFactory extends GuiContainer {
    private TileCrystalFactory tile;

    public GuiCrystalFactory(InventoryPlayer inventoryPlayer, TileCrystalFactory tileEntity) {
        super(new ContainerCrystalFactory(inventoryPlayer, tileEntity));
        tile = tileEntity;
        this.xSize += 18;
        this.ySize += 55;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        String smelt = "Smelt";
        String form = "Crystalize";
        this.buttonList.add(new GuiButtonCrystal(0, guiLeft + 31, guiTop + 107, fontRenderer.getStringWidth(smelt) + 10, 20, smelt, tile, 0));
        this.buttonList.add(new GuiButtonCrystal(1, guiLeft + 73, guiTop + 107, fontRenderer.getStringWidth(smelt) + 10, 20, smelt, tile, 1));
        this.buttonList.add(new GuiButtonCrystal(2, guiLeft + 114, guiTop + 107, fontRenderer.getStringWidth(smelt) + 10, 20, smelt, tile, 2));
        this.buttonList.add(new GuiButtonCrystal(3, guiLeft + 135, guiTop + 70, fontRenderer.getStringWidth(form) + 4, 20, form, tile, 3));
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = StatCollector.translateToLocal(tile.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) - 50, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 5, this.ySize - 95 + 2, 4210752);
        super.drawGuiContainerForegroundLayer(x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation(Strings.MOD_ID.toLowerCase(), "textures/gui/factory.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        int il;
        il = tile.getCookProgressScaled(24);
        drawTexturedModalRect(guiLeft + 137, guiTop + 42, 210, 0, il + 1, 16);
        
        il = tile.getEnergyScaled(77);
        drawTexturedModalRect(guiLeft + 7, guiTop + 103 - il, 194, 0, 16, il);
        
        drawTanks();
        
        List<String> energy = new ArrayList<String>();
        energy.add(tile.getCurrentCharge() + "/" + tile.getChargeCapcity() + " Jewels");
        drawToolTipOverArea(mouseX, mouseY, guiLeft + 7, guiTop + 27, guiLeft + 22, guiTop + 103, energy, mc.fontRenderer);
    }
    
    public void drawTanks() {
        
    }
    
    public int getValueScaled(int value, int max, int scale) {
        return (value * scale) / max;
    }
    
    public void drawToolTipOverArea(int mouseX, int mouseY, int minX, int minY, int maxX, int maxY, List<String> list, FontRenderer font) {
        if (list != null && font != null) {
            if ((mouseX >= minX && mouseX <= maxX) && (mouseY >= minY && mouseY <= maxY))
                drawHoveringText(list, mouseX, mouseY, font);
        }
    }
    
    public void drawFluid(int x, int y, FluidStack fluid, int width, int height) {
        if (fluid == null || fluid.getFluid() == null)
            return;
        mc.renderEngine.bindTexture(new ResourceLocation("textures/atlas/blocks.png"));
        GL11.glColor3ub((byte) (fluid.getFluid().getColor() >> 16 & 0xFF), (byte) (fluid.getFluid().getColor() >> 8 & 0xFF), (byte) (fluid.getFluid().getColor() & 0xFF));
        drawTiledTexture(x, y, fluid.getFluid().getIcon(fluid), width, height);
    }
    
    public void drawTiledTexture(int x, int y, Icon icon, int width, int height) {
        int i = 0;
        int j = 0;

        int drawHeight = 0;
        int drawWidth = 0;

        for (i = 0; i < width; i += 16) {
            for (j = 0; j < height; j += 16) {
                drawWidth = (width - i) < 16 ? (width - i) : 16;
                drawHeight = (height - j) < 16 ? (height - j) : 16;
                drawScaledTexturedModelRectFromIcon(x + i, y + j, icon, drawWidth, drawHeight);
            }
        }
        GL11.glColor4f(1f, 1f, 1f, 1F);
    }
    
    public void drawScaledTexturedModelRectFromIcon(int x, int y, Icon icon, int width, int height) {
        if (icon == null)
            return;

        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, this.zLevel, minU, minV + (maxV - minV) * height / 16F);
        tessellator.addVertexWithUV(x + width, y + height, this.zLevel, minU + (maxU - minU) * width / 16F, minV + (maxV - minV) * height / 16F);
        tessellator.addVertexWithUV(x + width, y + 0, this.zLevel, minU + (maxU - minU) * width / 16F, minV);
        tessellator.addVertexWithUV(x + 0, y + 0, this.zLevel, minU, minV);
        tessellator.draw();
    }
}
