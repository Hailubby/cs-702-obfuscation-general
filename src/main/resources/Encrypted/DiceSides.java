package com.jjhhh.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 30/04/2017.
 */

public class DiceSides {

    private List<Integer> faceList = new ArrayList<>();
    private int sum = 1;
    private int diceCount = 0;

    public  void addSide (int side) {
        this.faceList.add(side);
    }

    public void removeSide (int index) {
        if (index < faceList.size() && index >= 0) {
            this.faceList.remove(index);
        } else {
            this.sum++;
        }
    }

    public int getSum() {
        return this.sum;
    }

    public boolean compareSumDiceCount() {
        return sum < diceCount;
    }

    public void setDiceCount(int sum, int diceCount) {
        this.diceCount = (compareSumDiceCount()) ? this.sum : this.diceCount;
    }

    public void initialiseSides() {
        for(int i = 0; i < 20; i++) {
            faceList.add(i);
        }
    }
}
