package net.lomeli.cb.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.tile.TileCrystal;

public class ElementRegistry {

    private static Random rand;
    private static List<IElement> elements = new ArrayList<IElement>();

    public static IElement fire, water, earth, air, dark;

    public static void registerElements() {
        rand = new Random();

        fire = new ElementFire().setID(elements.size());
        elements.add(fire);
        water = new ElementWater().setID(elements.size());
        elements.add(water);
        earth = new ElementEarth().setID(elements.size());
        elements.add(earth);
        air = new ElementAir().setID(elements.size());
        elements.add(air);
        dark = new ElementDark().setID(elements.size());
        elements.add(dark);
    }

    public static void writeElementToItem(ItemStack item, int firstElement, int ability1, int secondElement, int ability2,
            int powerElement, boolean recessive) {
        if(item.getTagCompound() == null)
            item.stackTagCompound = new NBTTagCompound();
        item.getTagCompound().setInteger("element1", firstElement);
        item.getTagCompound().setInteger("element1Ability", ability1);
        item.getTagCompound().setInteger("element2", secondElement);
        item.getTagCompound().setInteger("element2Ability", ability2);
        item.getTagCompound().setInteger("powerElement", powerElement);
        item.getTagCompound().setBoolean("powerElementAbility", recessive);
    }

    public static void writeBasic(ItemStack item, int firstElement, int secondElement, int powerElement) {
        writeElementToItem(item, firstElement, rand.nextInt(4), secondElement, rand.nextInt(4), powerElement, false);
    }

    public static int getElementID(IElement element) {
        for(int i = 0; i < elements.size(); i++) {
            if(elements.get(i).equals(element))
                return i;
        }
        return 0;
    }

    public static IElement getAbiltiyOneElement(ItemStack stack) {
        if(stack.getTagCompound() != null) {
            int id = stack.getTagCompound().getInteger("element1");
            return elements.get(id);
        }
        return null;
    }

    public static CrystalAbility getAbilityOne(ItemStack stack) {
        if(stack.getTagCompound() != null && getAbiltiyOneElement(stack) != null) {
            return getAbiltiyOneElement(stack).abilities()[stack.getTagCompound().getInteger("element1Ability")];
        }
        return null;
    }

    public static IElement getAbiltiyTwoElement(ItemStack stack) {
        if(stack.getTagCompound() != null) {
            int id = stack.getTagCompound().getInteger("element2");
            return elements.get(id);
        }
        return null;
    }

    public static CrystalAbility getAbilityTwo(ItemStack stack) {
        if(stack.getTagCompound() != null && getAbiltiyTwoElement(stack) != null) {
            return getAbiltiyTwoElement(stack).abilities()[stack.getTagCompound().getInteger("element2Ability")];
        }
        return null;
    }

    public static IElement getPowerElement(ItemStack stack) {
        if(stack.getTagCompound() != null) {
            int id = stack.getTagCompound().getInteger("powerElement");
            return elements.get(id);
        }
        return null;
    }

    public static CrystalAbility getPower(ItemStack stack) {
        if(stack.getTagCompound() != null && getPowerElement(stack) != null) {
            boolean recessive = stack.getTagCompound().getBoolean("powerElementAbility");
            return recessive ? getPowerElement(stack).recessiveNeutral() : getPowerElement(stack).dominantNeutral();
        }
        return null;
    }

    public static void setCrystalAbilities(ItemStack stack, World world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(tile != null) {
            if(tile instanceof TileCrystal) {
                ((TileCrystal) tile).setAbilityOne(getAbilityOne(stack));
                ((TileCrystal) tile).setAbilityTwo(getAbilityTwo(stack));
                ((TileCrystal) tile).setPowerAbility(getPower(stack));
            }
        }
    }
}
