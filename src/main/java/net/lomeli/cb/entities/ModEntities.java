package net.lomeli.cb.entities;

import java.awt.Color;

import net.lomeli.cb.lib.Strings;

import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities {
    public static void loadEntities() {
        registerEntity(EntityFireWolf.class, "firewolf", 70, 2, 7, Color.red.getRGB(), Color.lightGray.getRGB(), new Type[] { Type.NETHER });
        registerEntity(EntityDarkChicken.class, "darkChicken", 65, 2, 5, new Color(72, 0, 117).getRGB(), new Color(181, 0, 0).getRGB(), new Type[] { Type.HILLS,
                Type.FOREST, Type.JUNGLE, Type.MAGICAL, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND });
        registerEntity(EntityThunderCow.class, "thunderCow", 65, 2, 6, new Color(79, 70, 57).getRGB(), Color.yellow.getRGB(), new Type[] { Type.HILLS, Type.FOREST,
                Type.JUNGLE, Type.MAGICAL, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND });
        registerEntity(EntityGhostPig.class, "ghostPig", 65, 2, 6, Color.pink.getRGB(), Color.yellow.getRGB(), new Type[] { Type.HILLS, Type.FOREST, Type.JUNGLE,
                Type.MAGICAL, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND });
    }

    @SuppressWarnings("unchecked")
    public static void registerEntity(Class<? extends EntityLiving> entityClass, String name, int spawnProb, int min, int max, int mainEggColor, int spotColor,
            Type[] typeList) {
        int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, Strings.MOD_ID.toLowerCase() + ":" + name, id);
        EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, mainEggColor, spotColor));
        for (Type type : typeList) {
            EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(type));
        }
    }
}
