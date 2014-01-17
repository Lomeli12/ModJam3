package net.lomeli.cb.client.gui;

import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonCrystal extends GuiButton{
    private TileCrystalFactory tile;
    private int func;
    
    public GuiButtonCrystal(int id, int x, int y, int buttonWidth, int buttonHeight, String par6Str, TileCrystalFactory tileEntity, int type) {
        super(id, x, y, buttonWidth, buttonHeight, par6Str);
        tile = tileEntity;
        func = type;
    }

    @Override
    public void mouseReleased(int par1, int par2) {
        switch (func){
        case 0:
            tile.process(0); break;
        case 1:
            tile.process(1); break;
        case 2:
            tile.process(2); break;
        case 3:
            tile.startFormationProcess(); break;
        }
    }
    
}
