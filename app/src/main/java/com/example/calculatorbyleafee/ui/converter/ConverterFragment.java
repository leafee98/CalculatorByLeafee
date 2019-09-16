package com.example.calculatorbyleafee.ui.converter;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.calculatorbyleafee.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.calculatorbyleafee.ui.sharedTools.CustomAdapter;

public class ConverterFragment extends Fragment {

    private static class InputHandler implements View.OnClickListener {

        private TextView activeTextView;
        private TextView inactiveTextView;
        private Spinner typeSpinner;
        private Spinner activeSpinner;
        private Spinner inactiveSpinner;

        private InputHandler(Spinner typeSpinner, TextView tv1, TextView tv2, Spinner unit1, Spinner unit2) {
            this.typeSpinner = typeSpinner;
            this.activeTextView = tv1;
            this.inactiveTextView = tv2;
            this.activeSpinner = unit1;
            this.inactiveSpinner = unit2;
            activeTextView.setBackgroundResource(R.drawable.textview_background_bold);
            inactiveTextView.setBackgroundResource(R.drawable.textview_background);
        }

        @Override
        public void onClick(View view) {
            char appendChar = 0;
            switch (view.getId()) {
                case R.id.textView_value1:
                case R.id.textView_value2:
                    if (!activeTextView.equals(view)) {
                        inactiveTextView = activeTextView;
                        activeTextView = (TextView)view;

                        Spinner tmp = activeSpinner;
                        activeSpinner = inactiveSpinner;
                        inactiveSpinner = tmp;
                    }

                    activeTextView.setBackgroundResource(R.drawable.textview_background_bold);
                    inactiveTextView.setBackgroundResource(R.drawable.textview_background);

                    break;

                case R.id.button_backspace:
                    String str = activeTextView.getText().toString();
                    if (str.length() > 0)
                        str = str.substring(0, str.length() - 1);
                    activeTextView.setText(str);
                    break;

                case R.id.button_clearAll:
                    activeTextView.setText("");
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
                case R.id.button_numPoint:
                    if (activeTextView.getText().toString().indexOf('.')== -1)
                        appendChar = '.';
                    break;
            }

            if (appendChar != ' ') {
                if (appendChar != 0) {
                    String originalNum = activeTextView.getText().toString();
                    String updatedNum = originalNum + appendChar;
                    activeTextView.setText(updatedNum);
                }
            }
            this.doConvert();
        }
        public void doConvert() {
            String convertType = typeSpinner.getSelectedItem().toString();
            String originalUnit = activeSpinner.getSelectedItem().toString();
            String targetUnit = inactiveSpinner.getSelectedItem().toString();
            double original = 0;
            try {
                original = Double.parseDouble(activeTextView.getText().toString());
            } catch (NumberFormatException e) {
                if (e.getMessage().equals("empty String")) {
                    // do nothing
                } else {
                    inactiveTextView.setText("Error Number Format");
                }
            }
            double target = ConverterModel.convert(convertType,
                    originalUnit, original, targetUnit);
            inactiveTextView.setText(String.format("%.7f", target));
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_converter, container, false);

        final Spinner spinnerConverterType = root.findViewById(R.id.spinner_converter_type);
        final Spinner spinnerUnit1 = root.findViewById(R.id.spinner_unit1);
        final Spinner spinnerUnit2 = root.findViewById(R.id.spinner_unit2);
        final TextView textView1 = root.findViewById(R.id.textView_value1);
        final TextView textView2 = root.findViewById(R.id.textView_value2);
        final InputHandler handler = new InputHandler(spinnerConverterType, textView2,  textView1,
                spinnerUnit1, spinnerUnit2);

        int[] buttonIds= {
                R.id.textView_value1, R.id.textView_value2,
                R.id.button_clearAll, R.id.button_backspace,
                R.id.button_num0, R.id.button_num1, R.id.button_num2, R.id.button_num3,
                R.id.button_num4, R.id.button_num5, R.id.button_num6, R.id.button_num7,
                R.id.button_num8, R.id.button_num9, R.id.button_numPoint
        };

        for (int i = 0; i < buttonIds.length; ++i) {
            root.findViewById(buttonIds[i]).setOnClickListener(handler);
        }

        final List<String> ConverterType = Arrays.asList("Length", "Time", "Data");
        final Map<String, List<String>> Units = new HashMap<>();
        Units.put("Length", Arrays.asList("nanometers", "microns", "millimeters", "centimeters",
                "meters", "kilometers", "inches", "yards", "miles", "nautical miles"));
        Units.put("Time", Arrays.asList("microseconds", "milliseconds", "seconds", "minutes",
                "hours", "days", "weeks", "years"));
        Units.put("Data", Arrays.asList("Bits", "Bytes", "KibiBits", "KibiBytes",
                "MebiBits", "MebiBytes", "GibiBits", "GibiBytes"));
        CustomAdapter ConverterTypeAdapter = new CustomAdapter(getContext(),
                ConverterType);
        final CustomAdapter LengthAdapter = new CustomAdapter(getContext(), Units.get("Length"));
        final CustomAdapter TimeAdapter = new CustomAdapter(getContext(), Units.get("Time"));
        final CustomAdapter DataAdapter = new CustomAdapter(getContext(), Units.get("Data"));

        spinnerConverterType.setAdapter(ConverterTypeAdapter);
        spinnerConverterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)parent.getItemAtPosition(position);
                if (selected.equals("Length")) {
                    spinnerUnit1.setAdapter(LengthAdapter);
                    spinnerUnit2.setAdapter(LengthAdapter);
                } else if (selected.equals("Time")) {
                    spinnerUnit1.setAdapter(TimeAdapter);
                    spinnerUnit2.setAdapter(TimeAdapter);
                } else if (selected.equals("Data")) {
                    spinnerUnit1.setAdapter(DataAdapter);
                    spinnerUnit2.setAdapter(DataAdapter);
                }

                textView1.setText("");
                textView2.setText("");

                AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        handler.doConvert();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        handler.doConvert();
                    }
                };
                spinnerUnit1.setOnItemSelectedListener(listener);
                spinnerUnit2.setOnItemSelectedListener(listener);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
        spinnerUnit1.setAdapter(LengthAdapter);
        spinnerUnit2.setAdapter(LengthAdapter);

        return root;
    }
}
