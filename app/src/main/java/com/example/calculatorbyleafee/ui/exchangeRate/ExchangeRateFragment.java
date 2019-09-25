package com.example.calculatorbyleafee.ui.exchangeRate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.calculatorbyleafee.R;
import com.example.calculatorbyleafee.ui.sharedTools.CustomAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ExchangeRateFragment extends Fragment {

    private View root;
    private ProgressBar progressBarLoadRate;
    private TextView textViewCurrentRate;
    private Spinner spinnerCurrency1;
    private Spinner spinnerCurrency2;
    private TextView textViewValue1;
    private TextView textViewValue2;
    private int activeView;
    private List<Button> buttonViews;
    private double rate;

    private class ButtonListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            TextView activeTextView = (activeView == 1) ? textViewValue1 : textViewValue2;
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

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            loadingRate();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
        }
    }

    private class TextViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            loadingRate();
            if (v.getId() == R.id.textView_value1) {
                switchActive(1);
            } else if (v.getId() == R.id.textView_value2) {
                switchActive(2);
            }
        }
    }

    private void switchActive(int i) {
        activeView = i;
        switch (activeView) {
            case 1:
                textViewValue1.setBackgroundResource(R.drawable.textview_background_bold);
                textViewValue2.setBackgroundResource(R.drawable.textview_background);
                break;
            case 2:
                textViewValue1.setBackgroundResource(R.drawable.textview_background);
                textViewValue2.setBackgroundResource(R.drawable.textview_background_bold);
                break;
        }
    }

    private void loadingRate() {
        textViewCurrentRate.setVisibility(View.GONE);
        progressBarLoadRate.setVisibility(View.VISIBLE);
        disableAllButton();


        final Handler finishLoad = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.w("handler", "handle the message from getting rate");
                if (msg.what == 0) {
                    finishLoadingRate(null);
                } else {
                    finishLoadingRate((String)msg.obj);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                String originalCurrency;
                String targetCurrency;
                if (activeView == 1) {
                    originalCurrency = spinnerCurrency1.getSelectedItem().toString();
                    targetCurrency = spinnerCurrency2.getSelectedItem().toString();
                } else {
                    originalCurrency = spinnerCurrency2.getSelectedItem().toString();
                    targetCurrency = spinnerCurrency1.getSelectedItem().toString();
                }

                Message msg = new Message();
                double rateGotten;
                try {
                    rateGotten = ExchangeRateModel.getRate(originalCurrency, targetCurrency);
                    msg.what = 0;
                    rate = rateGotten;
                } catch (ExchangeRateModel.GetRateException e) {
                    e.printStackTrace();
                    msg.what = -1;
                    msg.obj = e.getMessage();
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = -1;
                    msg.obj = e.getMessage();
                }

                finishLoad.sendMessage(msg);
            }
        }.start();
    }

    private void finishLoadingRate(String errorMsg) {
        if (errorMsg == null) {
            String originalCurrency;
            String targetCurrency;
            if (activeView == 1) {
                originalCurrency = spinnerCurrency1.getSelectedItem().toString();
                targetCurrency = spinnerCurrency2.getSelectedItem().toString();
            } else {
                originalCurrency = spinnerCurrency2.getSelectedItem().toString();
                targetCurrency = spinnerCurrency1.getSelectedItem().toString();
            }
            textViewCurrentRate.setText(String.format(Locale.ENGLISH,
                    "%s : %s  =  1 : %f", originalCurrency, targetCurrency, rate));
            enableAllButton();
            doConvert();
        } else {
            textViewCurrentRate.setText(errorMsg);
        }
        textViewCurrentRate.setVisibility(View.VISIBLE);
        progressBarLoadRate.setVisibility(View.GONE);
    }

    private void disableAllButton() {
        for (int i = 0; i < buttonViews.size(); ++i) {
            buttonViews.get(i).setClickable(false);
            buttonViews.get(i).setEnabled(false);
        }
    }

    private void enableAllButton() {
        for (int i = 0; i < buttonViews.size(); ++i) {
            buttonViews.get(i).setClickable(true);
            buttonViews.get(i).setEnabled(true);
        }
    }

    private void doConvert() {
        if (activeView == 1) {
            String original = textViewValue1.getText().toString();
            textViewValue2.setText(ExchangeRateModel.getTarget(original, rate));
        } else {
            String original = textViewValue2.getText().toString();
            textViewValue1.setText(ExchangeRateModel.getTarget(original, rate));
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
        buttonViews = new ArrayList<>();
        for (int buttonId : buttonIds) {
            buttonViews.add((Button)root.findViewById(buttonId));
            buttonViews.get(buttonViews.size() - 1).setOnClickListener(buttonListener);
        }
    }

    private void assignViewOnClick() {
        SpinnerListener spinnerListener = new SpinnerListener();
        TextViewClickListener textViewClickListener = new TextViewClickListener();
        textViewValue1.setClickable(true);
        textViewValue2.setClickable(true);
        textViewValue1.setOnClickListener(textViewClickListener);
        textViewValue2.setOnClickListener(textViewClickListener);
        spinnerCurrency1.setOnItemSelectedListener(spinnerListener);
        spinnerCurrency2.setOnItemSelectedListener(spinnerListener);

    }

    private void assignView() {
        progressBarLoadRate = root.findViewById(R.id.progressBar_loadRate);
        textViewCurrentRate = root.findViewById(R.id.textView_currentRate);
        spinnerCurrency1 = root.findViewById(R.id.spinner_currency1);
        spinnerCurrency2 = root.findViewById(R.id.spinner_currency2);
        textViewValue1 = root.findViewById(R.id.textView_value1);
        textViewValue2 = root.findViewById(R.id.textView_value2);
    }

    private void assignSpinner() {
        CustomAdapter currencyAdapter = new CustomAdapter(getContext(), Arrays.asList("usd", "cny"));

        spinnerCurrency1.setAdapter(currencyAdapter);
        spinnerCurrency2.setAdapter(currencyAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_exchange_rate, container, false);

        assignView();
        assignButtonOnClick();
        assignViewOnClick();
        assignSpinner();

        if (savedInstanceState != null) {
            switchActive(savedInstanceState.getInt("active"));
            textViewValue1.setText(savedInstanceState.getString("currency1"));
            textViewValue2.setText(savedInstanceState.getString("currency2"));
            spinnerCurrency1.setSelection(savedInstanceState.getInt("currencyType1"));
            spinnerCurrency2.setSelection(savedInstanceState.getInt("currencyType2"));
        }


        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String currency1 = textViewValue1.getText().toString();
        String currency2 = textViewValue2.getText().toString();
        int currencyType1 = spinnerCurrency1.getSelectedItemPosition();
        int currencyType2 = spinnerCurrency2.getSelectedItemPosition();

        outState.putString("currency1", currency1);
        outState.putString("currency2", currency2);
        outState.putInt("currencyType1", currencyType1);
        outState.putInt("currencyType2", currencyType2);
        outState.putInt("active", activeView);
    }
}
