package net.lomeli.cb.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PageText {
    //public static List<String> pagesLeft = new ArrayList<String>();
    //public static List<String> pagesRight = new ArrayList<String>();
    
    public static void loadPages() {
        
        
    }
    
    /*
    public static String getPageLeft(ItemStack book) {
        String text = null;
        if(book.stackTagCompound != null)
            text = pagesLeft.get(book.getTagCompound().getInteger("currentPage"));
        else {
            book.stackTagCompound = new NBTTagCompound();
            book.getTagCompound().setInteger("currentPage", 0);
            text = pagesLeft.get(0);
        }
        return text;
    }
    
    public static String getPageRight(ItemStack book) {
        String text = null;
        if(book.stackTagCompound != null)
            text = pagesRight.get(book.getTagCompound().getInteger("currentPage"));
        else {
            book.stackTagCompound = new NBTTagCompound();
            book.getTagCompound().setInteger("currentPage", 0);
            text = pagesRight.get(0);
        }
        return text;
    }
    */
}
