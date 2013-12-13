package net.lomeli.cb.element;

import java.util.ArrayList;
import java.util.List;

public class ElementBase {

    private static List<IElement> elements = new ArrayList<IElement>();

    private static IElement fire, water, earth, air, dark;

    public static void registerElements() {
        earth = new ElementEarth();

        elements.add(fire);
        elements.add(water);
        elements.add(earth);
        elements.add(air);
        elements.add(dark);
    }
}
