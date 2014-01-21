package net.lomeli.cb.client.gui;

import net.lomeli.cb.client.gui.pages.PageBase;
import net.lomeli.cb.client.gui.pages.PageTitle;

public class BookPages {
    private static String baseText = "book.crystalbearers:";
    private static String title = baseText + "manual";
    
    public static void setPages(GuiDatabase gui) {
        GuiDatabase.pagesLeft.clear();
        GuiDatabase.pagesRight.clear();
        addPage(null, new PageTitle(gui, "By: Lomeli12", title));
    }
    
    private static void addPage(PageBase left, PageBase right) {
        GuiDatabase.pagesLeft.add(left);
        GuiDatabase.pagesRight.add(right);
    }
}
