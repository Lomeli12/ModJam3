package net.lomeli.cb.core.handler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.element.ElementRegistry;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.tile.TileCrystal;

public class BlockBreakHandler {
    @ForgeSubscribe
    public void blockBreak(BreakEvent event) {
        if(event.block.blockID == ModBlocks.crystal.blockID && !event.getPlayer().capabilities.isCreativeMode) {
            TileCrystal tile = (TileCrystal) event.world.getBlockTileEntity(event.x, event.y, event.z);
            if(tile != null) {
                if(tile.isNatural()) {
                    if(!event.world.isRemote) {
                        EntityItem item = new EntityItem(event.world, event.x, event.y, event.z, new ItemStack(ModItems.shard, 1,
                                event.world.rand.nextInt(4)));
                        event.world.spawnEntityInWorld(item);
                    }
                }else {
                    if(!event.world.isRemote) {
                        ItemStack dropItem = new ItemStack(ModItems.crystalItem);
                        ElementRegistry.writeElementToItem(dropItem, tile.firstEle, tile.ability1ID, tile.secondEle,
                                tile.ability2ID, tile.thridEle, tile.passiveAbility);
                        EntityItem item = new EntityItem(event.world, event.x, event.y, event.z, dropItem);
                        event.world.spawnEntityInWorld(item);
                    }
                }
                if(!event.getPlayer().getEntityData().getBoolean("hasText")){
                    
                }
            }
        }
    }
}
