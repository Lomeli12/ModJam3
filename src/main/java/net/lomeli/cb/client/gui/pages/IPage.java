package net.lomeli.cb.client.gui.pages;

public interface IPage {
    public abstract void draw();
    
    public abstract String getTag();
    
    public abstract boolean needsTag();
}
