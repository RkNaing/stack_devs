package com.rk.stackdevs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public final class Badge {

    @Expose
    @SerializedName("bronze")
    private int bronze;

    @Expose
    @SerializedName("silver")
    private int silver;

    @Expose
    @SerializedName("gold")
    private int gold;

    public int getBronze() {
        return bronze;
    }

    public void setBronze(int bronze) {
        this.bronze = bronze;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean hasData(){
        return bronze > 0 || silver > 0 || gold > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return bronze == badge.bronze &&
                silver == badge.silver &&
                gold == badge.gold;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bronze, silver, gold);
    }

    @Override
    public String toString() {
        return "Badge{" +
                "bronze=" + bronze +
                ", silver=" + silver +
                ", gold=" + gold +
                '}';
    }
}
