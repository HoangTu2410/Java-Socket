package model;

import java.io.Serializable;

public class UserStat extends User implements Serializable{
    private float winRate;
    private int winNumber;
    private int totalGainedPoint;

    public UserStat() {
        super();
    }

    public UserStat(float winRate, int winNumber, int totalGainedPoint) {
        super();
        this.winRate = winRate;
        this.winNumber = winNumber;
        this.totalGainedPoint = totalGainedPoint;
    }

    public float getWinRate() {
        return winRate;
    }

    public void setWinRate(float winRate) {
        this.winRate = winRate;
    }

    public int getWinNumber() {
        return winNumber;
    }

    public void setWinNumber(int winNumber) {
        this.winNumber = winNumber;
    }

    public int getTotalGainedPoint() {
        return totalGainedPoint;
    }

    public void setTotalGainedPoint(int totalGainedPoint) {
        this.totalGainedPoint = totalGainedPoint;
    }
    
}
