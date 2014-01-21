package net.lomeli.cb.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.lomeli.cb.client.gui.pages.PageBase;
import net.lomeli.cb.lib.Strings;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiDatabase extends GuiScreen {
    public static List<PageBase> pagesLeft = new ArrayList<PageBase>();
    public static List<PageBase> pagesRight = new ArrayList<PageBase>();
    private ItemStack book;
    private EntityPlayer player;

    private int bookImageWidth = 256, bookImageHeight = 207, guiLeft, guiTop, currentPage, maxPages;
    public int pageLeftX, pageRightX, pageY;
    private GuiButtonNextPage buttonNextPage, buttonPreviousPage;

    public GuiDatabase(ItemStack book, EntityPlayer player) {
        BookPages.setPages(this);

        this.book = book;
        this.player = player;
        currentPage = getPage();
    }

    @Override
    public void onGuiClosed() {
        book.getTagCompound().setInteger("currentPage", currentPage);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();

        if (pagesLeft.size() > pagesRight.size())
            maxPages = pagesLeft.size();
        else
            maxPages = pagesRight.size();

        guiLeft = (width - bookImageWidth) / 2;
        guiTop = (height - bookImageHeight) / 2;
        pageLeftX = guiLeft + 15;
        pageRightX = guiLeft + 132;
        pageY = guiTop + 13;

        byte b0 = 2;
        this.buttonList.add(buttonNextPage = new GuiButtonNextPage(0, guiLeft + 120, b0 + 154, true));
        this.buttonList.add(buttonPreviousPage = new GuiButtonNextPage(1, guiLeft + 38, b0 + 154, false));
    }

    protected void actionPerformed(GuiButton button) {
        if (button != null) {
            if (button.equals(buttonNextPage)) {
                if (currentPage < maxPages)
                    currentPage++;
            } else if (button.equals(buttonPreviousPage)) {
                if (currentPage > 0)
                    currentPage--;
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation(Strings.MOD_ID.toLowerCase(), "textures/gui/book.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, bookImageWidth, bookImageHeight);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();

        PageBase leftPage = pagesLeft.get(currentPage);
        if (leftPage != null) {
            if (!leftPage.needsTag())
                leftPage.setPos(pageLeftX, pageY).draw();
            else {
                if (doesPlayerHaveTag(leftPage.getTag()))
                    leftPage.setPos(pageLeftX, pageY).draw();
            }
        }

        PageBase rightPage = pagesRight.get(currentPage);
        if (rightPage != null) {
            if (!rightPage.needsTag())
                rightPage.setPos(pageRightX, pageY).draw();
            else {
                if (doesPlayerHaveTag(rightPage.getTag()))
                    rightPage.setPos(pageRightX, pageY).draw();
            }
        }

        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }

    public boolean doesPlayerHaveTag(String tag) {
        return player.getEntityData().getBoolean(tag);
    }

    public int getPage() {
        if (book.getTagCompound() == null)
            book.stackTagCompound = new NBTTagCompound();
        return book.getTagCompound().getInteger("currentPage");
    }
}
