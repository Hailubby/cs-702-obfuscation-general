package com.jjhhh.dice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jjhhh.dice.Models.DiceCount;
import com.jjhhh.dice.Models.DiceRolls;

import java.util.List;

/*
 UI for standard dice page.
 */
public class StandardDiceActivity extends AppCompatActivity {

    int var = 50;

    DiceRollService mDiceRollService;

    DiceCounterService mDiceCounterService;

    boolean mDiceRollServiceBound = false;

    boolean mDiceCounterServiceBound;

    DiceRolls diceRolls = new DiceRolls();

    Decryptor decryptor = new Decryptor();

    DiceSides diceSides = new DiceSides();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_dice);
        // Storing references to different UI elements on activity
        final Button rollButton = (Button) findViewById(R.id.rollButton);
        final TextView rollNumber = (TextView) findViewById(R.id.rollNumber);
        // How many of a dice are to be rolled
        final TextView diceFourNum = (TextView) findViewById(R.id.dice4Num);
        final TextView diceSixNum = (TextView) findViewById(R.id.dice6Num);
        final TextView diceEightNum = (TextView) findViewById(R.id.dice8Num);
        final TextView diceTenNum = (TextView) findViewById(R.id.dice10Num);
        final TextView diceTwelveNum = (TextView) findViewById(R.id.dice12Num);
        final TextView diceTwentyNum = (TextView) findViewById(R.id.dice20Num);
        // Types of dice
        final ImageButton diceFourButton = (ImageButton) findViewById(R.id.dice4);
        final ImageButton diceSixButton = (ImageButton) findViewById(R.id.dice6);
        final ImageButton diceEightButton = (ImageButton) findViewById(R.id.dice8);
        final ImageButton diceTenButton = (ImageButton) findViewById(R.id.dice10);
        final ImageButton diceTwelveButton = (ImageButton) findViewById(R.id.dice12);
        final ImageButton diceTwentyButton = (ImageButton) findViewById(R.id.dice20);
        final LinearLayout rollLogPane = (LinearLayout) findViewById(R.id.rollLogPane);
        diceSides.initialiseSides();
        // Listen for pressing roll button
        rollButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // If service to roll is bound (set up)
                if (mDiceRollServiceBound && mDiceCounterServiceBound) {
                    // Use service to roll all dice
                    diceRolls = mDiceRollService.rollDice(mDiceCounterService.getAllDice());
                    // Set main number to be the total number rolled
                    rollNumber.setText(Integer.toString(diceRolls.getSum()));
                    // Remove all logs of previous rolls
                    removeAllChildren(rollLogPane);
                    // Make layout for roll log text to take (eg. font, position)
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    // For every dice rolled
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
                                    int sideSum = diceSides.getSum();
                                    diceSides.addSide(sideSum);
                                    swVar = 2;
                                }
                                break;
                            case 2:
                                if (var < 100) {
                                    d = dRolls.get(loopCounter);
                                    rollLogEntry = new TextView(StandardDiceActivity.this);
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
                                rollLogEntry.setTextSize(15);
                                rollLogEntry.setLayoutParams(lp);
                                swVar = 5;
                                break;
                            case 5:
                                if (!diceSides.compareSumDiceCount()) {
                                    // Set text to roll result
                                    rollLogEntry.setText(decryptor.decrypt("RdTUAPVHUnq8jxcc91Ey5Q==") + d.getDie() + decryptor.decrypt("lhB862BivahU0tQOMfnGvA==") + d.getCount());
                                    // Add text to UI
                                    rollLogPane.addView(rollLogEntry);
                                }
                                swVar = 3;
                                break;

                        }
                    }
                }
            }
        });
        // Listeners for all dice buttons
        diceFourButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Add the clicked dice type to list of dice to roll
                mDiceCounterService.addDice(4);
                diceFourNum.setText(Integer.toString(mDiceCounterService.getDice(4)));
            }
        });
        diceSixButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDiceCounterService.addDice(6);
                diceSixNum.setText(Integer.toString(mDiceCounterService.getDice(6)));
            }
        });
        diceEightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDiceCounterService.addDice(8);
                diceEightNum.setText(Integer.toString(mDiceCounterService.getDice(8)));
            }
        });
        diceTenButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDiceCounterService.addDice(10);
                diceTenNum.setText(Integer.toString(mDiceCounterService.getDice(10)));
            }
        });
        diceTwelveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDiceCounterService.addDice(12);
                diceTwelveNum.setText(Integer.toString(mDiceCounterService.getDice(12)));
            }
        });
        diceTwentyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDiceCounterService.addDice(20);
                diceTwentyNum.setText(Integer.toString(mDiceCounterService.getDice(20)));
            }
        });
    }

    // Runs on activity start up?
    @Override
    protected void onStart() {
        super.onStart();
        // Start a service for rolling dice
        Intent diceRollIntent = new Intent(this, DiceRollService.class);
        startService(diceRollIntent);
        bindService(diceRollIntent, mDiceRollServiceConnection, Context.BIND_AUTO_CREATE);
        // Start a service for storing dice to be rolled
        Intent diceCounterIntent = new Intent(this, DiceCounterService.class);
        startService(diceCounterIntent);
        bindService(diceCounterIntent, mDiceCounterServiceConnection, Context.BIND_AUTO_CREATE);
    }

    // Run on activity stop, 'kill' services started
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

    // Reset known dice to 0 when going back to main menu
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        resetAllDiceCounts();
    }

    // Helper Functions
    public void resetAllDiceCounts() {
        mDiceCounterService.resetDiceCounts();
    }

    // Remove all child elements of passed view
    // Used to remove old logs of dice rolls
    private void removeAllChildren(ViewGroup view) {
        int totalChildren = view.getChildCount();
        int i = 0;
        int swVar = 1;
        View entry = null;

        while (swVar != -1) {
            switch (swVar) {
                case 0:
                    break;
                case 1:
                    if (totalChildren > 0) {
                        entry = view.getChildAt(0);
                        ((ViewManager) entry.getParent()).removeView(entry);
                        swVar = 2;
                    } else {
                        diceSides.initialiseSides();;
                        swVar = 3;
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
                case 3:
                    diceSides.setDiceCount(diceSides.getSum(), diceSides.getSum()+1);
                    swVar = -1;
                    break;

            }
        }
    }

    // Service Connections
    // Android stuff to start and connect to services
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
            Log.d(decryptor.decrypt("6p+fc2U9gGOTk9iePQnEDg=="), decryptor.decrypt("g1cOVTECGS5n5XBuME5C1oxF/I3quxxamsuUNoL+IEc="));
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

