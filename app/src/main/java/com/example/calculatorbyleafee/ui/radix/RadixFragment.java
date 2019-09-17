package com.example.calculatorbyleafee.ui.radix;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.calculatorbyleafee.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RadixFragment extends Fragment {

    private TextView textViewHex;
    private TextView textViewDec;
    private TextView textViewOct;
    private TextView textViewBin;

    private List<Button> allButtons;
    private List<Button> hexButtons;
    private List<Button> decButtons;
    private List<Button> octButtons;
    private List<Button> binButtons;

    private RadixModel radixModel;
    private enum Radix { HEX, DEC, OCT, BIN }
    private Radix currentRadix;

    private void assignInputAction() {
        View.OnClickListener buttonInput = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = null;
                switch (currentRadix) {
                    case HEX: val = textViewHex.getText().toString(); break;
                    case DEC: val = textViewDec.getText().toString(); break;
                    case OCT: val = textViewOct.getText().toString(); break;
                    case BIN: val = textViewBin.getText().toString(); break;
                }

                char appendChar = 0;
                switch (view.getId()) {
                    case R.id.button_backspace:
                        if (val.length() == 1) {
                            val = "0";
                        } else {
                            val = val.substring(0, val.length() - 1);
                        }
                        break;
                    case R.id.button_clearAll: val = "0"; break;
                    case R.id.button_num0: appendChar = '0'; break;
                    case R.id.button_num1: appendChar = '1'; break;
                    case R.id.button_num2: appendChar = '2'; break;
                    case R.id.button_num3: appendChar = '3'; break;
                    case R.id.button_num4: appendChar = '4'; break;
                    case R.id.button_num5: appendChar = '5'; break;
                    case R.id.button_num6: appendChar = '6'; break;
                    case R.id.button_num7: appendChar = '7'; break;
                    case R.id.button_num8: appendChar = '8'; break;
                    case R.id.button_num9: appendChar = '9'; break;
                    case R.id.button_numA: appendChar = 'A'; break;
                    case R.id.button_numB: appendChar = 'B'; break;
                    case R.id.button_numC: appendChar = 'C'; break;
                    case R.id.button_numD: appendChar = 'D'; break;
                    case R.id.button_numE: appendChar = 'E'; break;
                    case R.id.button_numF: appendChar = 'F'; break;
                }

                if (appendChar != 0) {
                    if (val.equals("0")) {
                        val = String.valueOf(appendChar);
                    } else {
                        val += appendChar;
                    }
                }

                updateTextView(val);
            }
        };

        for (int i = 0; i < allButtons.size(); ++i) {
            allButtons.get(i).setOnClickListener(buttonInput);
        }
    }

    private void updateTextView(String val) {
        switch (currentRadix) {
            case HEX:
                this.radixModel.setHex(val);
                textViewHex.setText(val);
                textViewDec.setText(this.radixModel.getDec());
                textViewOct.setText(this.radixModel.getOct());
                textViewBin.setText(this.radixModel.getBin());
                break;
            case DEC:
                this.radixModel.setDec(val);
                textViewDec.setText(val);
                textViewHex.setText(this.radixModel.getHex());
                textViewOct.setText(this.radixModel.getOct());
                textViewBin.setText(this.radixModel.getBin());
                break;
            case OCT:
                this.radixModel.setOct(val);
                textViewOct.setText(val);
                textViewHex.setText(this.radixModel.getHex());
                textViewDec.setText(this.radixModel.getDec());
                textViewBin.setText(this.radixModel.getBin());
                break;
            case BIN:
                this.radixModel.setBin(val);
                textViewBin.setText(val);
                textViewHex.setText(this.radixModel.getHex());
                textViewDec.setText(this.radixModel.getDec());
                textViewOct.setText(this.radixModel.getOct());
                break;
        }

    }

    private void assignTextViewOnClick() {
        View.OnClickListener textViewOnClick;
        textViewOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.textView_hex: switchRadix(Radix.HEX); break;
                    case R.id.textView_dec: switchRadix(Radix.DEC); break;
                    case R.id.textView_oct: switchRadix(Radix.OCT); break;
                    case R.id.textView_bin: switchRadix(Radix.BIN); break;
                }
            }
        };

        textViewHex.setClickable(true);
        textViewDec.setClickable(true);
        textViewOct.setClickable(true);
        textViewBin.setClickable(true);

        textViewHex.setOnClickListener(textViewOnClick);
        textViewDec.setOnClickListener(textViewOnClick);
        textViewOct.setOnClickListener(textViewOnClick);
        textViewBin.setOnClickListener(textViewOnClick);
    }

    private void assignViews(View root) {
        allButtons = new ArrayList<>();
        List<Integer> allButtonsId = Arrays.asList(
                R.id.button_num0, R.id.button_num1, R.id.button_num2, R.id.button_num3,
                R.id.button_num4, R.id.button_num5, R.id.button_num6, R.id.button_num7,
                R.id.button_num8, R.id.button_num9, R.id.button_numA, R.id.button_numB,
                R.id.button_numC, R.id.button_numD, R.id.button_numE, R.id.button_numF,
                R.id.button_backspace, R.id.button_clearAll
        );
        for (int i = 0; i < allButtonsId.size(); ++i) {
            allButtons.add((Button)root.findViewById(allButtonsId.get(i)));
        }
        this.hexButtons = allButtons.subList(0, 16);
        this.decButtons = allButtons.subList(0, 10);
        this.octButtons = allButtons.subList(0, 8);
        this.binButtons = allButtons.subList(0, 2);

        this.textViewHex = root.findViewById(R.id.textView_hex);
        this.textViewDec = root.findViewById(R.id.textView_dec);
        this.textViewOct = root.findViewById(R.id.textView_oct);
        this.textViewBin = root.findViewById(R.id.textView_bin);
    }

    private void disableButton(List<Button> buttons) {
        for (int i = 0; i < buttons.size(); ++i) {
            buttons.get(i).setEnabled(false);
            buttons.get(i).setClickable(false);
        }
    }

    private void enableButton(List<Button> buttons) {
        for (int i = 0; i < buttons.size(); ++i) {
            buttons.get(i).setEnabled(true);
            buttons.get(i).setClickable(true);
        }
    }

    private void switchBoldTextView() {
        textViewHex.setBackgroundResource(R.drawable.textview_background);
        textViewDec.setBackgroundResource(R.drawable.textview_background);
        textViewOct.setBackgroundResource(R.drawable.textview_background);
        textViewBin.setBackgroundResource(R.drawable.textview_background);
        switch (currentRadix) {
            case HEX:
                textViewHex.setBackgroundResource(R.drawable.textview_background_bold);
                break;
            case DEC:
                textViewDec.setBackgroundResource(R.drawable.textview_background_bold);
                break;
            case OCT:
                textViewOct.setBackgroundResource(R.drawable.textview_background_bold);
                break;
            case BIN:
                textViewBin.setBackgroundResource(R.drawable.textview_background_bold);
                break;
        }
    }

    private void switchRadix(Radix radix) {
        disableButton(hexButtons);
        switch (radix) {
            case HEX: enableButton(hexButtons); currentRadix = Radix.HEX; break;
            case DEC: enableButton(decButtons); currentRadix = Radix.DEC; break;
            case OCT: enableButton(octButtons); currentRadix = Radix.OCT; break;
            case BIN: enableButton(binButtons); currentRadix = Radix.BIN; break;
        }
        switchBoldTextView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = layoutInflater.inflate(R.layout.fragment_radix, container, false);

        this.assignViews(root);
        this.assignTextViewOnClick();
        this.assignInputAction();
        this.radixModel = new RadixModel();
        this.switchRadix(Radix.DEC);
        this.updateTextView("0");

        return root;
    }
}
