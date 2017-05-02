package com.jjhhh.dice.Models;

import java.util.ArrayList;
import java.util.List;

// Stores list of dice rolls (results of rolling dice)
public class DiceRolls {

    // sum of all rolls
    private int sum;

    // results of individual rolls
    private List<DiceCount> rolls;

    // Constructors
    public DiceRolls(int sum, List<DiceCount> rolls) {
        this.sum = sum;
        this.rolls = rolls;
    }

    public DiceRolls() {
        this.sum = 0;
        this.rolls = new ArrayList<>();
    }

    // getters
    public int getSum() {
        return sum;
    }

    public List<DiceCount> getRolls() {
        return rolls;
    }
}

