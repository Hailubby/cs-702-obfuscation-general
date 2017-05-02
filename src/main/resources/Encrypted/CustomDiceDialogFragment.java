package com.jjhhh.dice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

// Pop up window for picking a number of sides for a custom die
public class CustomDiceDialogFragment extends android.app.DialogFragment {

    private NumberPicker mDiceNumberPicker;

    Decryptor decryptor = new Decryptor();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View npView = inflater.inflate(R.layout.number_picker_dialog_layout, null);
        // References a UI element
        mDiceNumberPicker = (NumberPicker) npView.findViewById(R.id.numberPicker);
        // Set min/max selectable values
        mDiceNumberPicker.setMinValue(2);
        mDiceNumberPicker.setMaxValue(100);
        // Use the Builder class for convenient dialog construction
        // Sets up buttons, titles, UI stuff
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(decryptor.decrypt("XCj5/9bOPALGoF68o2T+tNhDYoRrKY1bGWaRBEXpj4Q=")).setView(npView).setPositiveButton(decryptor.decrypt("srtKXz4iWHsYUk08tqCYaw=="), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                ((CustomDiceActivity) getActivity()).callAddNewDiceService(mDiceNumberPicker.getValue());
            }
        }).setNegativeButton(decryptor.decrypt("5kcrVf2A+OTzz33JePQj8Q=="), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

