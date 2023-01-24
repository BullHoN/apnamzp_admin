package com.avit.apnamzpadmin.models.user;

public class DistanceBasePricings {
    private int BELOW_THREE;
    private int BELOW_SIX;

    public DistanceBasePricings(int BELOW_THREE, int BELOW_SIX) {
        this.BELOW_THREE = BELOW_THREE;
        this.BELOW_SIX = BELOW_SIX;
    }

    public int getBELOW_THREE() {
        return BELOW_THREE;
    }

    public int getBELOW_SIX() {
        return BELOW_SIX;
    }

    public void setBELOW_THREE(int BELOW_THREE) {
        this.BELOW_THREE = BELOW_THREE;
    }

    public void setBELOW_SIX(int BELOW_SIX) {
        this.BELOW_SIX = BELOW_SIX;
    }
}
