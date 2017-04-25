package com.jjhhh.dice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class CustomDiceDialogFragment extends android.app.DialogFragment {

    private NumberPicker mDiceNumberPicker;

    Decryptor decryptor = new Decryptor();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View npView = inflater.inflate(R.layout.number_picker_dialog_layout, null);
        mDiceNumberPicker = (NumberPicker) npView.findViewById(R.id.numberPicker);
        mDiceNumberPicker.setMinValue(2);
        mDiceNumberPicker.setMaxValue(100);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(decryptor.decrypt("pF9rh54eCbHpUSy7HfPCI7Iesp4WAd5JEIwLWRzb83Q=")).setView(npView).setPositiveButton(decryptor.decrypt("lVVnU01Cig+/N8EXTK2f8g=="), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                ((CustomDiceActivity) getActivity()).callAddNewDiceService(mDiceNumberPicker.getValue());
            }
        }).setNegativeButton(decryptor.decrypt("7VjSAX6qtzvZZoTNUjSV3g=="), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

