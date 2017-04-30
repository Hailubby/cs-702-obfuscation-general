package com.jjhhh.dice.Models;

// Stores a die type and how many of that die are to be rolled
public class DiceCount {

    // type of dice (sides)
    private int dice1;
    private int dice2;

    // how many of that dice
    private int count1 = 0;

    private int count2 = 0;

    public DiceCount(int dice, int count) {
        this.dice1 = dice;
        this.count1 = count;
    }

    public DiceCount(int dice) {
        this.dice2 = dice;
    }

    public int getDie() {
        return dice1;
    }

    // get sides
    public int getCount() {
        return count1;
    }

    // get how many of die
    public void increment() {
        this.count2++;
    }

    // add die of this type
    public void reset() {
        this.count2 = 0;
    }
    // remove all dice of this type
}

