package com.example.calculatorbyleafee.ui.converter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calculatorbyleafee.R;

import java.util.List;

import com.example.calculatorbyleafee.ui.sharedTools.CustomAdapter;

public class ConverterFragment extends Fragment {
    private View root;
    private Spinner spinnerType;
    private Spinner spinnerUnit1;
    private Spinner spinnerUnit2;
    private TextView textView1;
    private TextView textView2;
    private int activeView;

    private class ButtonListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            TextView activeTextView;
            activeTextView = (activeView == 1) ? textView1 : textView2;

            char appendChar = 0;
            String str = activeTextView.getText().toString();
            switch (view.getId()) {
                case R.id.button_backspace:
                    if (str.length() > 1)
                        str = str.substring(0, str.length() - 1);
                    else if (str.length() == 1)
                        str = "0";
                    activeTextView.setText(str);
                    break;

                case R.id.button_clearAll:
                    activeTextView.setText("0");
                    break;

                case R.id.button_numPoint:
                    if (str.indexOf('.')== -1)
                        appendChar = '.';
                    break;

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
            }

            if (appendChar != 0) {
                if (str.equals("0") || str.equals("0.0"))
                    str = "" + appendChar;
                else
                    str += appendChar;
                activeTextView.setText(str);
            }

            doConvert();
        }
    }

    private void doConvert() {
        String type = spinnerType.getSelectedItem().toString();
        String unit1 = spinnerUnit1.getSelectedItem().toString();
        String unit2 = spinnerUnit2.getSelectedItem().toString();
        String str1 = textView1.getText().toString();
        String str2 = textView2.getText().toString();
        double num1 = 0, num2 = 0;
        try { num1 = Double.parseDouble(str1); }
            catch (NumberFormatException e) {
            if (e.getMessage() != null && !e.getMessage().equals("empty String")) throw e; }
        try { num2 = Double.parseDouble(str2); }
            catch (NumberFormatException e) {
            if (e.getMessage() != null && !e.getMessage().equals("empty String")) throw e; }
        double result;
        switch (activeView) {
            case 1:
                result = ConverterModel.convert(type, unit1, num1, unit2);
                textView2.setText(String.valueOf(result));
                break;
            case 2:
                result = ConverterModel.convert(type, unit2, num2, unit1);
                textView1.setText(String.valueOf(result));
                break;
        }
    }

    private class TextViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switchActive((v.getId() == R.id.textView_value1) ? 1 : 2);
        }
    }

    private void switchActive(int i) {
        activeView = i;
        if (activeView == 1) {
            textView1.setBackgroundResource(R.drawable.textview_background_bold);
            textView2.setBackgroundResource(R.drawable.textview_background);
        } else {
            textView1.setBackgroundResource(R.drawable.textview_background);
            textView2.setBackgroundResource(R.drawable.textview_background_bold);
        }
    }

    private void assignButtonOnClick() {
        ButtonListener buttonListener = new ButtonListener();
        int[] buttonIds= {
                R.id.button_clearAll, R.id.button_backspace,
                R.id.button_num0, R.id.button_num1, R.id.button_num2, R.id.button_num3,
                R.id.button_num4, R.id.button_num5, R.id.button_num6, R.id.button_num7,
                R.id.button_num8, R.id.button_num9, R.id.button_numPoint
        };
        for (int buttonId : buttonIds)
            root.findViewById(buttonId).setOnClickListener(buttonListener);
    }

    private void assignTextViewOnClick() {
        TextViewClickListener textViewListener = new TextViewClickListener();
        textView1.setClickable(true);
        textView2.setClickable(true);
        textView1.setOnClickListener(textViewListener);
        textView2.setOnClickListener(textViewListener);
    }

    private class SpinnerUnitOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.w("SpinnerUnitOnItemSelectedListener", "doConvert() in here");
            doConvert();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
        }
    }

    private class SpinnerTypeOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.w("SpinnerTypeOnItemSelectedListener", "type selected, reset unit's spinner adapter");
            String type = parent.getItemAtPosition(position).toString();
            CustomAdapter subAdapter = new CustomAdapter(getContext(), ConverterModel.supportUnits.get(type));
            spinnerUnit1.setAdapter(subAdapter);
            spinnerUnit2.setAdapter(subAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.w("SpinnerTypeOnItemSelectedListener", "nothing selected");
            // do nothing
        }
    }

    private void assignSpinner() {
        Log.w("assignSpinner", "assigning spinner, set the type spinner adapter, and all spinner listener");
        List<String> supportTypes = ConverterModel.supportUnits.get("Types");
        CustomAdapter typeAdapter = new CustomAdapter(getContext(), supportTypes);

        SpinnerTypeOnItemSelectedListener typeListener = new SpinnerTypeOnItemSelectedListener();
        SpinnerUnitOnItemSelectedListener unitListener = new SpinnerUnitOnItemSelectedListener();
        spinnerType.setAdapter(typeAdapter);
        spinnerType.setOnItemSelectedListener(typeListener);
        spinnerUnit1.setOnItemSelectedListener(unitListener);
        spinnerUnit2.setOnItemSelectedListener(unitListener);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        int spinnerTypePosition = spinnerType.getSelectedItemPosition();
        int spinner1 = spinnerUnit1.getSelectedItemPosition();
        int spinner2 = spinnerUnit2.getSelectedItemPosition();
        String str1 = textView1.getText().toString();
        String str2 = textView2.getText().toString();

        outState.putInt("activeView", activeView);
        outState.putInt("spinnerType", spinnerTypePosition);
        outState.putInt("spinnerUnit1", spinner1);
        outState.putInt("spinnerUnit2", spinner2);
        outState.putString("textView1", str1);
        outState.putString("textView2", str2);

        Log.w("Saving spinnerPosition", "saving the Unit1 is: " + outState.getInt("spinnerUnit1"));
        Log.w("Saving spinnerPosition", "saving the Unit2 is: " + outState.getInt("spinnerUnit2"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.fragment_converter, container, false);

        spinnerType = root.findViewById(R.id.spinner_converter_type);
        spinnerUnit1 = root.findViewById(R.id.spinner_unit1);
        spinnerUnit2 = root.findViewById(R.id.spinner_unit2);
        textView1 = root.findViewById(R.id.textView_value1);
        textView2 = root.findViewById(R.id.textView_value2);

        this.assignButtonOnClick();
        this.assignTextViewOnClick();
        this.assignSpinner();

        if (savedInstanceState != null) {
            Log.w("spinnerPosition", "the Unit1 is: " + savedInstanceState.getInt("spinnerUnit1"));
            Log.w("spinnerPosition", "the Unit2 is: " + savedInstanceState.getInt("spinnerUnit2"));
            this.switchActive(savedInstanceState.getInt("activeView"));
            spinnerType.setSelection(savedInstanceState.getInt("spinnerType"));
            spinnerUnit1.setSelection(savedInstanceState.getInt("spinnerUnit1"));
            spinnerUnit2.setSelection(savedInstanceState.getInt("spinnerUnit2"));
            if (activeView == 1)
                textView1.setText(savedInstanceState.getString("textView1"));
            else
                textView2.setText(savedInstanceState.getString("textView2"));
        } else {
            textView1.setText("0");
            textView2.setText("0");
            this.switchActive(2);
        }

        return root;
    }
}
