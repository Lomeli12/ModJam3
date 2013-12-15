package net.lomeli.cb.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.ICrystal;

public class ItemScanner extends ItemCB{

    public ItemScanner(int par1) {
        super(par1, "scanner");
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":scanner");
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8,
            float par9, float par10) {
        if(!world.isRemote) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if(tile != null){
                if(tile instanceof ICrystal){
                    CrystalAbility[] abilities = new CrystalAbility[] { ((ICrystal)tile).abilityOne(), ((ICrystal)tile).abilityTwo(), ((ICrystal)tile).powerAbility() };
                    if(abilities.length > 0){
                        for(CrystalAbility ability : abilities){
                            if(ability != null){
                                player.addChatMessage(StatCollector.translateToLocal(ability.getAbilityName()));
                                player.addChatMessage(StatCollector.translateToLocal(ability.getAbilityDesc()));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
