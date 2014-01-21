package net.lomeli.cb.tile;

public interface IEnergy {
    public abstract int getCurrentCharge();

    public abstract int getChargeCapcity();

    public abstract int addCharge(int charge);

    public abstract int useCharge(int charge);

    public abstract boolean canCompleteTask(int charge);

    public abstract boolean isChargeBox();

    public abstract boolean isGenerator();
}
