package com.example.calculatorbyleafee.ui.numberConversion;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.calculatorbyleafee.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberConversionFragment extends Fragment {

    private static class InputHandler implements View.OnClickListener {

        private TextView activeTextView;
        private TextView inactiveTextView;

        private InputHandler(TextView tv1, TextView tv2) {
            this.activeTextView = tv1;
            this.inactiveTextView = tv2;
        }

        @Override
        public void onClick(View view) {
            char appendChar = ' ';
            switch (view.getId()) {
                case R.id.textView_value1:
                case R.id.textView_value2:
                    TextView tmp = activeTextView;
                    activeTextView = inactiveTextView;
                    inactiveTextView = tmp;
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
                case R.id.button_numA: appendChar = 'A'; break;
                case R.id.button_numB: appendChar = 'B'; break;
                case R.id.button_numC: appendChar = 'C'; break;
                case R.id.button_numD: appendChar = 'D'; break;
                case R.id.button_numE: appendChar = 'E'; break;
                case R.id.button_numF: appendChar = 'F'; break;
            }

            if (appendChar != ' ') {
                String originalNum = activeTextView.getText().toString();
                String updatedNum = originalNum + appendChar;
                activeTextView.setText(updatedNum);

                // todo: update inactiveTextView
            }
        }
    }

    private static class CustomAdapter extends BaseAdapter {
        private List<String> list;
        private Context context;

        public CustomAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.spinner_layout, viewGroup, false);
            TextView tv = view.findViewById(R.id.textViewOfSpinner);
            tv.setText(list.get(i));
            return tv;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_number_conversion, container, false);

        TextView textView1 = root.findViewById(R.id.textView_value1);
        TextView textView2 = root.findViewById(R.id.textView_value2);
        InputHandler handler = new InputHandler(textView1, textView2);

        int[] viewIds = {
                R.id.textView_value1, R.id.textView_value2,
                R.id.button_clear, R.id.button_backspace,
                R.id.button_num0, R.id.button_num1, R.id.button_num2, R.id.button_num3,
                R.id.button_num4, R.id.button_num5, R.id.button_num6, R.id.button_num7,
                R.id.button_num8, R.id.button_num9, R.id.button_numA, R.id.button_numB,
                R.id.button_numC, R.id.button_numD, R.id.button_numE, R.id.button_numF
        };

        for (int i = 0; i < viewIds.length; ++i) {
            root.findViewById(viewIds[i]).setOnClickListener(handler);
        }

        Spinner spinnerUnit1 = root.findViewById(R.id.spinner_unit1);
        Spinner spinnerUnit2 = root.findViewById(R.id.spinner_unit2);
        List<String> DecimalArr = Arrays.asList("DEC", "OCT", "HEX", "BIN");
        CustomAdapter adapter = new CustomAdapter(getContext(), DecimalArr);
        spinnerUnit1.setAdapter(adapter);
        spinnerUnit2.setAdapter(adapter);



        return root;
    }
}
