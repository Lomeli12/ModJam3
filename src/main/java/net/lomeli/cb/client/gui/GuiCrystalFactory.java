package net.lomeli.cb.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.lomeli.cb.core.handler.PacketHandler;
import net.lomeli.cb.inv.ContainerCrystalFactory;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import net.minecraftforge.fluids.FluidStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCrystalFactory extends GuiContainer {
    private TileCrystalFactory tile;
    private GuiButtonCrystal smelt1, smelt2, smelt3, crystalize;
    protected int mouseX = 0, mouseY = 0;

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
        String smelt = StatCollector.translateToLocal(Strings.SMELT);
        String form = StatCollector.translateToLocal(Strings.CRYSTALIZE);
        this.buttonList.add(smelt1 = new GuiButtonCrystal(0, guiLeft + 31, guiTop + 107, fontRenderer.getStringWidth(smelt) + 10, 20, smelt));
        this.buttonList.add(smelt2 = new GuiButtonCrystal(1, guiLeft + 73, guiTop + 107, fontRenderer.getStringWidth(smelt) + 10, 20, smelt));
        this.buttonList.add(smelt3 = new GuiButtonCrystal(2, guiLeft + 114, guiTop + 107, fontRenderer.getStringWidth(smelt) + 10, 20, smelt));
        this.buttonList.add(crystalize = new GuiButtonCrystal(3, guiLeft + 135, guiTop + 70, fontRenderer.getStringWidth(form) + 4, 20, form));
    }

    protected void actionPerformed(GuiButton button) {
        if (button != null) {
            System.out.println(button.displayString);
            if (button.equals(crystalize))
                PacketHandler.sendTileCrystalFactoryPacket(tile, 3, true);
            else
                PacketHandler.sendTileCrystalFactoryPacket(tile, button.id, false);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        smelt1.enabled = !tile.toDoStuff[0];
        smelt2.enabled = !tile.toDoStuff[1];
        smelt3.enabled = !tile.toDoStuff[2];
        crystalize.enabled = !tile.toDoStuff[3];
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = StatCollector.translateToLocal(tile.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) - 50, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 5, this.ySize - 95 + 2, 4210752);

        super.drawGuiContainerForegroundLayer(x, y);

        List<String> text = new ArrayList<String>();
        text.add(tile.getCurrentCharge() + "/" + tile.getChargeCapcity() + " Jewels");
        drawToolTipOverArea(mouseX, mouseY, 7, 26, 22, 102, text, fontRenderer);

        if (tile.tanks[0].getFluid() != null && tile.tanks[0].getFluid().getFluid() != null) {
            text.clear();
            text.add(tile.tanks[0].getFluid().getFluid().getUnlocalizedName());
            text.add(tile.tanks[0].getFluidAmount() + "/" + tile.tanks[0].getCapacity() + "mB");
            drawToolTipOverArea(mouseX, mouseY, 40, 39, 56, 82, text, fontRenderer);
        } else {
            text.clear();
            text.add("Empty");
            drawToolTipOverArea(mouseX, mouseY, 40, 39, 56, 82, text, fontRenderer);
        }
        
        if (tile.tanks[1].getFluid() != null && tile.tanks[1].getFluid().getFluid() != null) {
            text.clear();
            text.add(tile.tanks[1].getFluid().getFluid().getUnlocalizedName());
            text.add(tile.tanks[1].getFluidAmount() + "/" + tile.tanks[1].getCapacity() + "mB");
            drawToolTipOverArea(mouseX, mouseY, 82, 39, 97, 82, text, fontRenderer);
        } else {
            text.clear();
            text.add("Empty");
            drawToolTipOverArea(mouseX, mouseY, 82, 39, 97, 82, text, fontRenderer);
        }
        
        if (tile.tanks[2].getFluid() != null && tile.tanks[2].getFluid().getFluid() != null) {
            text.clear();
            text.add(tile.tanks[2].getFluid().getFluid().getUnlocalizedName());
            text.add(tile.tanks[2].getFluidAmount() + "/" + tile.tanks[2].getCapacity() + "mB");
            drawToolTipOverArea(mouseX, mouseY, 116, 39, 131, 82, text, fontRenderer);
        } else {
            text.clear();
            text.add("Empty");
            drawToolTipOverArea(mouseX, mouseY, 116, 39, 131, 82, text, fontRenderer);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation(Strings.MOD_ID.toLowerCase(), "textures/gui/factory.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int il;
        il = tile.getCookProgressScaled(24);
        drawTexturedModalRect(guiLeft + 137, guiTop + 42, 210, 0, il + 1, 16);

        il = getValueScaled(tile.getCurrentCharge(), tile.getChargeCapcity(), 77);
        drawTexturedModalRect(guiLeft + 7, guiTop + 103 - il, 194, 0, 16, il);

        drawTanks();
    }

    public void drawTanks() {
        int j;
        if (tile.tanks[0].getFluid() != null && tile.tanks[0].getFluid().getFluid() != null) {
            j = getValueScaled(tile.tanks[0].getFluidAmount(), tile.tanks[0].getCapacity(), 43);
            this.drawFluid(guiLeft + 40, guiTop + 39 - j, tile.tanks[0].getFluid(), 16, j);
        }
        if (tile.tanks[1].getFluid() != null && tile.tanks[1].getFluid().getFluid() != null) {
            j = getValueScaled(tile.tanks[1].getFluidAmount(), tile.tanks[1].getCapacity(), 43);
            this.drawFluid(guiLeft + 82, guiTop + 39 - j, tile.tanks[1].getFluid(), 16, j);
        }
        if (tile.tanks[2].getFluid() != null && tile.tanks[2].getFluid().getFluid() != null) {
            j = getValueScaled(tile.tanks[2].getFluidAmount(), tile.tanks[2].getCapacity(), 43);
            this.drawFluid(guiLeft + 116, guiTop + 39 - j, tile.tanks[2].getFluid(), 16, j);
        }
    }

    @Override
    public void handleMouseInput() {
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

        mouseX = x - guiLeft;
        mouseY = y - guiTop;

        super.handleMouseInput();
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

    @SuppressWarnings("rawtypes")
    protected void drawTooltipHoveringTextf(List list, int x, int y, FontRenderer font) {
        if (list == null || list.isEmpty())
            return;

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        int k = 0;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            int l = font.getStringWidth(s);

            if (l > k) {
                k = l;
            }
        }
        int i1 = x + 12;
        int j1 = y - 12;
        int k1 = 8;

        if (list.size() > 1)
            k1 += 2 + (list.size() - 1) * 10;

        if (i1 + k > this.width)
            i1 -= 28 + k;

        if (j1 + k1 + 6 > this.height)
            j1 = this.height - k1 - 6;

        this.zLevel = 300.0F;
        itemRenderer.zLevel = 300.0F;
        int l1 = -267386864;
        this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
        this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
        this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
        this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
        this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
        int i2 = 1347420415;
        int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
        this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
        this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
        this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
        this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

        for (int k2 = 0; k2 < list.size(); ++k2) {
            String s1 = (String) list.get(k2);
            font.drawStringWithShadow(s1, i1, j1, -1);

            if (k2 == 0)
                j1 += 2;

            j1 += 10;
        }
        this.zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
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
