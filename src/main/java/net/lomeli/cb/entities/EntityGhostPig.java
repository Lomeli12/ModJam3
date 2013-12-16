package net.lomeli.cb.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGhostPig extends EntityMob{

    public EntityGhostPig(World par1World) {
        super(par1World);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(4, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(20.0D);
    }

    public boolean isAIEnabled() {
        return true;
    }

    public void setAttackTarget(EntityLivingBase par1EntityLivingBase) {
        super.setAttackTarget(par1EntityLivingBase);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(12, Byte.valueOf((byte) 0));
        this.getDataWatcher().addObject(13, Byte.valueOf((byte) 0));
        this.getDataWatcher().addObject(14, Byte.valueOf((byte) 0));
    }

    protected void playStepSound(int par1, int par2, int par3, int par4) {
        this.playSound("mob.pig.step", 0.15F, 1.0F);
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);

    }

    protected String getLivingSound() {
        return "mob.pig.say";
    }

    protected String getHurtSound() {
        return "mob.pig.hurt";
    }

    protected String getDeathSound() {
        return "mob.pig.death";
    }

    protected float getSoundVolume() {
        return 0.4F;
    }
    
    public void onUpdate() {
        super.onUpdate();
    }

    public float getEyeHeight() {
        return this.height * 0.8F;
    }

    public int getVerticalFaceSpeed() {
        return super.getVerticalFaceSpeed();
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if(!super.attackEntityFrom(par1DamageSource, par2))
            return false;
        else {
            EntityLivingBase entitylivingbase = this.getAttackTarget();

            if(entitylivingbase == null && this.getEntityToAttack() instanceof EntityLivingBase) {
                entitylivingbase = (EntityLivingBase) this.getEntityToAttack();
            }

            if(entitylivingbase == null && par1DamageSource.getEntity() instanceof EntityLivingBase) {
                entitylivingbase = (EntityLivingBase) par1DamageSource.getEntity();
            }
            return true;
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        par1Entity.motionY += 20F;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) 2);
    }

    public int getMaxSpawnedInChunk() {
        return 8;
    }

    protected boolean canDespawn() {
        return true;
    }

}
