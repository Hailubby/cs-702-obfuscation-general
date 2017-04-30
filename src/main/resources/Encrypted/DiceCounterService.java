package com.jjhhh.dice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.jjhhh.dice.Models.DiceCount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Service that keeps counts of known dice
public class DiceCounterService extends Service {

    // Android thing
    private IBinder mBinder = new DiceCounterBinder();

    // Map of known dice - sides, number of this dice
    private HashMap<Integer, Integer> diceCounts = new HashMap<>();

    private DiceCount dice = new DiceCount(20);

    public DiceCounterService() {
    }

    // Android thing to bind service to activity
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // Add a dice to the map
    public void addDice(int diceType) {
        // If dice already known
        if (diceCounts.containsKey(diceType)) {
            // Add another dice of this type to the map
            diceCounts.put(diceType, diceCounts.get(diceType) + 1);
        } else {
            // Add new dice to the map, we have 1 of this dice to start
            diceCounts.put(diceType, 1);
        }
    }

    // gets a list of all dice we know about, and how many of each dice we have
    public List<DiceCount> getAllDice() {
        List<DiceCount> diceCountList = new ArrayList<>();
        int swVar = 1;
        int loopCounter = 0;
        int loopSize = 0;
        Object[] keySetArray = null;
        while (swVar != 0) {
            switch (swVar) {
                case 1:
                    loopCounter = 0;
                    loopSize = diceCounts.keySet().size();
                    if (loopSize > 0) {
                        keySetArray = diceCounts.keySet().toArray();
                        swVar = 2;
                    } else {
                        swVar = 0;
                    }
                    break;
                case 2:
                    dice.increment();
                    swVar = 4;
                    break;
                case 3:
                    loopCounter++;
                    if (loopCounter < loopSize) {
                        dice.increment();
                        swVar = 2;
                    } else {
                        dice.reset();
                        swVar = 0;
                    }
                    break;
                case 4:
                    int k = (int) keySetArray[loopCounter];
                    diceCountList.add(new DiceCount(k, diceCounts.get(k)));
                    swVar = 3;
                    break;
            }
        }
        return diceCountList;
    }

    // Gets the number of dice for a specific type of die
    public int getDice(int diceType) {
        if (!diceCounts.containsKey(diceType)) {
            return 0;
        }
        return diceCounts.get(diceType);
    }

    public boolean hasDice(int diceType) {
        return diceCounts.containsKey((diceType));
    }

    // Reset all known dice
    public void resetDiceCounts() {
        diceCounts = new HashMap<>();
    }

    // Android thing
    public class DiceCounterBinder extends Binder {

        DiceCounterService getService() {
            return DiceCounterService.this;
        }
    }
}

