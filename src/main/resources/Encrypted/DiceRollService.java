package com.jjhhh.dice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.jjhhh.dice.Models.DiceCount;
import com.jjhhh.dice.Models.DiceRolls;
import java.util.ArrayList;
import java.util.List;

public class DiceRollService extends Service {

    // Android stuff
    private IBinder mBinder = new DiceRollBinder();

    DiceSides diceSides = new DiceSides();

    public DiceRollService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // Rolls all dice passed in list
    public DiceRolls rollDice(List<DiceCount> diceToRoll) {
        // sum of rolls
        int sum = 0;
        // results of rolls
        List<DiceCount> results = new ArrayList<>();
        // For each die type in the list
        int caseVar = 0;
        int c2 = 0;
        DiceCount die = null;
        int c1 = 0;
        int size = diceToRoll.size();

        while (caseVar != -1) {
            switch (caseVar) {
                case 0:
                    if (size > 0) {
                        die = diceToRoll.get(c1);
                        caseVar = 3;
                    } else {
                        diceSides.addSide(1);
                        diceSides.addSide(2);
                        diceSides.addSide(3);
                        diceSides.addSide(4);
                        diceSides.addSide(5);
                        diceSides.addSide(6);
                        caseVar = 1;
                    }
                    break;
                case 1:
                    int prevSum = 0;
                    int newRes = (int) (Math.floor((Math.random() * 6)) + 1);

                    prevSum += newRes * Math.random();
                    diceSides.setDiceCount(prevSum, newRes);
                    caseVar = -1;
                    break;
                case 2:
                    c2++;
                    if (c2 < die.getCount()) {
                        caseVar = 0;
                    } else if (c2 >= 0) {
                        c1++;
                        if (c1 < size) {
                            c2 = 0;
                            caseVar = 0;
                        } else {
                            caseVar = 1;
                        }
                    }
                    break;
                case 3:
                    int res = (int) (Math.floor((Math.random() * die.getDie())) + 1);

                    if(!diceSides.compareSumDiceCount()) {
                        sum += res;
                        results.add(new DiceCount(die.getDie(), res));
                    } else {
                        diceSides.removeSide(res);
                    }
                    caseVar = 2;
                    break;
            }
        }
        return new DiceRolls(sum, results);
    }

    // Android stuff
    public class DiceRollBinder extends Binder {

        DiceRollService getService() {
            return DiceRollService.this;
        }
    }
}

