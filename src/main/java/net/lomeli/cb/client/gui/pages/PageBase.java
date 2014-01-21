package net.lomeli.cb.client.gui.pages;

import org.lwjgl.opengl.GL11;

import net.lomeli.cb.client.gui.SmallFontRenderer;
import net.lomeli.cb.core.ClientProxy;
import net.lomeli.cb.lib.Strings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PageBase {
    public int width = 110, height = 180, x = 0, y = 0;
    public GuiScreen gui;
    protected static RenderItem itemRenderer = new RenderItem();
    protected Minecraft mc;
    private String tag;
    private boolean requiresTag;
    public static final FontRenderer largeFontRenderer = FMLClientHandler.instance().getClient().fontRenderer;
    public static final SmallFontRenderer smallFontRenderer = ClientProxy.smallFontRenderer;

    public PageBase(GuiScreen gui) {
        this.gui = gui;
        this.mc = Minecraft.getMinecraft();
        requiresTag = false;
    }
    
    public PageBase(int x, int y, GuiScreen gui) {
        this.gui = gui;
        this.mc = Minecraft.getMinecraft();
        requiresTag = false;
        this.setPos(x, y);
    }
    
    public PageBase setPos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public PageBase setTag(String tag) {
        this.tag = tag;
        requiresTag = true;
        return this;
    }

    public void draw() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public String getTag() {
        return Strings.MOD_ID.toLowerCase() + ":" + tag;
    }
    
    public boolean needsTag() {
        return requiresTag;
    }
}
