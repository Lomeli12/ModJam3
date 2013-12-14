package net.lomeli.cb.element;

import java.util.ArrayList;
import java.util.List;

public class ElementRegistry {

    private static List<IElement> elements = new ArrayList<IElement>();

    public static IElement fire, water, earth, air, dark;

    public static void registerElements() {
        fire = new ElementFire();
        water = new ElementWater();
        earth = new ElementEarth();
        air = new ElementAir();
        dark = new ElementDark();

        elements.add(fire);
        elements.add(water);
        elements.add(earth);
        elements.add(air);
        elements.add(dark);
    }
}
