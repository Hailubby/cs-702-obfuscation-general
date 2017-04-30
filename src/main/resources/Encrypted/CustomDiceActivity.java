package com.jjhhh.dice;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jjhhh.dice.Models.DiceCount;
import com.jjhhh.dice.Models.DiceRolls;

import java.util.List;

// UI for rolling custom types of dice
public class CustomDiceActivity extends AppCompatActivity {

    DiceRollService mDiceRollService;

    DiceCounterService mDiceCounterService;

    boolean mDiceRollServiceBound = false;

    boolean mDiceCounterServiceBound = false;

    DiceRolls diceRolls = new DiceRolls();

    Decryptor decryptor = new Decryptor();

    DiceSides diceSides = new DiceSides();

    int var = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dice);
        // References to UI elements
        final Button rollButton = (Button) findViewById(R.id.rollButton);
        final TextView rollNumber = (TextView) findViewById(R.id.rollNumber);
        final LinearLayout rollLogPane = (LinearLayout) findViewById(R.id.rollLogPane);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        diceSides.initialiseSides();
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        // Show a popup on clicking fab button
        rollButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // If services have started up and bound properly
                if (mDiceRollServiceBound && mDiceCounterServiceBound) {
                    // Rolls dice on pressing roll button
                    diceRolls = mDiceRollService.rollDice(mDiceCounterService.getAllDice());
                    rollNumber.setText(Integer.toString(diceRolls.getSum()));
                    // Reset log of dice rolls
                    removeAllChildren(rollLogPane);
                    // Add logs of dices rolls to log
                    // UI Layout
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //TODO Control flatten
//                    for (DiceCount d : diceRolls.getRolls()) {
//                        TextView rollLogEntry = new TextView(CustomDiceActivity.this);
//                        // Text sizing/font
//                        rollLogEntry.setTextSize(15);
//                        rollLogEntry.setLayoutParams(lp);
//                        // Set text to roll result
//                        rollLogEntry.setText(decryptor.decrypt("RdTUAPVHUnq8jxcc91Ey5Q==") + d.getDie() + decryptor.decrypt("lhB862BivahU0tQOMfnGvA==") + d.getCount());
//                        // Add text to UI
//                        rollLogPane.addView(rollLogEntry);
//                    }
                    int loopCounter = 0;
                    int loopSize = 0;
                    int swVar = 1;
                    DiceCount d = null;
                    List<DiceCount> dRolls = diceRolls.getRolls();
                    TextView rollLogEntry = null;

                    while (swVar > 0) {
                        switch (swVar) {
                            case 1:
                                loopSize = diceRolls.getRolls().size();
                                if (loopSize == 0) {
                                    swVar = 0;
                                } else {
                                    diceSides.addSide(loopCounter);
                                    swVar = 2;
                                }
                                break;
                            case 2:
                                if (var < 100) {
                                    d = dRolls.get(loopCounter);
                                    rollLogEntry = new TextView(CustomDiceActivity.this);
                                } else {
                                    d = dRolls.get(loopSize - 1);
                                }
                                swVar = 4;
                                break;
                            case 3:
                                loopCounter++;
                                if (loopSize > loopCounter) {
                                    swVar = 2;
                                } else {
                                    swVar = 0;
                                }
                                break;
                            case 4:
                                // Text sizing/font
                                if(!diceSides.compareSumDiceCount()) {
                                    rollLogEntry.setTextSize(15);
                                    rollLogEntry.setLayoutParams(lp);
                                } else {
                                    diceSides.setDiceCount(loopSize * 2 - 1, loopCounter - swVar);
                                }
                                swVar = 5;
                                break;
                            case 5:
                                // Set text to roll result
                                rollLogEntry.setText(decryptor.decrypt("RdTUAPVHUnq8jxcc91Ey5Q==") + d.getDie() + decryptor.decrypt("lhB862BivahU0tQOMfnGvA==") + d.getCount());
                                // Add text to UI
                                rollLogPane.addView(rollLogEntry);
                                swVar = 3;
                                break;

                        }
                    }
                }
            }
        });
    }

    public void callAddNewDiceService(int i) {
        final int num = i;
        boolean hasDice = mDiceCounterService.hasDice(num);
        if (hasDice) {
            return;
        }
        // Create a new button to place in UI for new custom type of dice
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout buttonContainer = (LinearLayout) findViewById(R.id.buttonContainer);
        Button customDiceButton = new Button(CustomDiceActivity.this);
        // Create text for how many of this dice are to be rolled
        final TextView customButtonText = new TextView(CustomDiceActivity.this);
        // Add button and text to UI
        buttonContainer.addView(customDiceButton);
        buttonContainer.addView(customButtonText);
        // Set up UI stuff for button and text (string, positions)
        customDiceButton.setText(decryptor.decrypt("RPl0vcIwukuC/acYxcvx6A==") + i);
        customDiceButton.setLayoutParams(lp);
        customButtonText.setText(decryptor.decrypt("RPl0vcIwukuC/acYxcvx6A==") + 0);
        customButtonText.setLayoutParams(lp);
        customButtonText.setGravity(Gravity.CENTER);
        // Listen for pressing dice button
        customDiceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Add dice to list of dice to roll
                mDiceCounterService.addDice(num);
                // Update text
                customButtonText.setText(Integer.toString((mDiceCounterService.getDice(num))));
            }
        });
        mDiceCounterService.addDice(num);
        customButtonText.setText(Integer.toString((mDiceCounterService.getDice(num))));
    }

    // Start services to track dice to roll and for rolling dice
    @Override
    protected void onStart() {
        super.onStart();
        Intent diceRollIntent = new Intent(this, DiceRollService.class);
        startService(diceRollIntent);
        bindService(diceRollIntent, mDiceRollServiceConnection, Context.BIND_AUTO_CREATE);
        Intent diceCounterIntent = new Intent(this, DiceCounterService.class);
        startService(diceCounterIntent);
        bindService(diceCounterIntent, mDiceCounterServiceConnection, Context.BIND_AUTO_CREATE);
    }

    // Stop services when activity stops
    @Override
    protected void onStop() {
        super.onStop();
        if (mDiceRollServiceBound) {
            unbindService(mDiceRollServiceConnection);
            mDiceRollServiceBound = false;
        }
        if (mDiceCounterServiceBound) {
            unbindService(mDiceCounterServiceConnection);
            mDiceCounterServiceBound = false;
        }
    }

    // Reset counts of dice when press back
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        resetAllDiceCounts();
    }

    // Helper Functions
    // Remove child elements of a UI view
    // Used to remove logs from log
    private void removeAllChildren(ViewGroup view) {
        int totalChildren = view.getChildCount();
        //TODO control flatten
//        for (int i = 0; i < totalChildren; i++) {
//            View entry = view.getChildAt(0);
//            ((ViewManager) entry.getParent()).removeView(entry);
//        }
        int i = 0;
        int swVar = 1;
        View entry;

        while (swVar != -1) {
            switch (swVar) {
                case 0:
                    diceSides.removeSide(i);
                    swVar = -1;
                    break;
                case 1:
                    if (totalChildren > 0) {
                        entry = view.getChildAt(0);
                        ((ViewManager) entry.getParent()).removeView(entry);
                        swVar = 2;
                    } else {
                        diceSides.addSide(i);
                        swVar = 0;
                    }
                    break;
                case 2:
                    i++;
                    if (i < totalChildren) {
                        swVar = 1;
                    } else {
                        swVar = -1;
                    }
                    break;
            }
        }
    }

    // Show number picker pop up
    public void showDialog() {
        FragmentManager fm = getFragmentManager();
        android.app.DialogFragment newFragment = new CustomDiceDialogFragment();
        newFragment.show(fm, decryptor.decrypt("Nbx2cO64699Yk9ETN7LqTw=="));
    }

    // Reset counts of dice
    public void resetAllDiceCounts() {
        mDiceCounterService.resetDiceCounts();
    }

    // Service Connections
    // Android stuff to use services
    private ServiceConnection mDiceCounterServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DiceCounterService.DiceCounterBinder diceCounterBinder = (DiceCounterService.DiceCounterBinder) service;
            mDiceCounterService = diceCounterBinder.getService();
            mDiceCounterServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDiceCounterServiceBound = false;
        }
    };

    private ServiceConnection mDiceRollServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(decryptor.decrypt("WDNd/RY6B/CY3LC3sLpfcw=="), decryptor.decrypt("QA2Bmj4OHOTVgN7G7InLXN6u0aW/hhebknDW91ZXz0E="));
            DiceRollService.DiceRollBinder diceRollBinder = (DiceRollService.DiceRollBinder) service;
            mDiceRollService = diceRollBinder.getService();
            mDiceRollServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDiceRollServiceBound = false;
        }
    };
}

