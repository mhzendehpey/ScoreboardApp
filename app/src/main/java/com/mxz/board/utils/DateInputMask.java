package com.mxz.board.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

public class DateInputMask implements TextWatcher {

    private String current = "";
    private static final String ddmmyyyy = "DDMMYYYY";
    private final Calendar cal = Calendar.getInstance();
    private final EditText input;

    public DateInputMask(EditText input) {
        this.input = input;
        this.input.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().equals(current)) {
            return;
        }

        String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
        String cleanC = current.replaceAll("[^\\d.]|\\.", "");

        int cl = clean.length();
        int sel = cl;
        for (int i = 2; i <= cl && i < 6; i += 2) {
            sel++;
        }
        //Fix for pressing delete next to a forward slash
        if (clean.equals(cleanC)) sel--;

        if (clean.length() < 8) {
            clean = clean + ddmmyyyy.substring(clean.length());
        } else {
            //This part makes sure that when we finish entering numbers
            //the date is correct, fixing it otherwise
            int day = Integer.parseInt(clean.substring(0, 2));
            int mon = Integer.parseInt(clean.substring(2, 4));
            int year = Integer.parseInt(clean.substring(4, 8));

            mon = mon < 1 ? 1 : Math.min(mon, 12);
            cal.set(Calendar.MONTH, mon - 1);
            year = (year < 1300) ? 1300 : Math.min(year, 2100);
            cal.set(Calendar.YEAR, year);
            // ^ first set year for the line below to work correctly
            //with leap years - otherwise, date e.g. 29/02/2012
            //would be automatically corrected to 28/02/2012

            day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
            clean = String.format("%02d%02d%02d", day, mon, year);
        }

        clean = String.format("%s/%s/%s", clean.substring(0, 2),
                clean.substring(2, 4),
                clean.substring(4, 8));

        sel = Math.max(sel, 0);
        current = clean;
        input.setText(current);
        input.setSelection(Math.min(sel, current.length()));
    }


    @Override
    public void afterTextChanged(Editable s) {

    }
}