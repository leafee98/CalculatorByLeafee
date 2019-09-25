package com.example.calculatorbyleafee.ui.exchangeRate;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class ExchangeRateModel {

    static class GetRateException extends Exception {

        static final long serialVersionUID = 7834659287346191237L;

        GetRateException(String msg) {
            super(msg);
        }

    }

    static double getRate(String scur, String tcur) throws JSONException, GetRateException {
        if (scur.equals(tcur)) {
            return 1.0;
        } else {
            String jsonContent;
            try {
                String querySite = "http://haobaoshui.com:8008/exchangerate/v1/rate?scur=%s&tcur=%s";
                URL queryUrl = new URL(String.format(querySite, scur, tcur));
                HttpURLConnection connection = (HttpURLConnection) queryUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(3000);

                Log.w("ExchangeRateModel scur is", scur);
                Log.w("ExchangeRateModel tcur is", tcur);
                Log.w("ExchangeRateModel URL", String.format(querySite, scur, tcur));

                // DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                // output.writeBytes(String.format("scur=%s&tcur=%s", scur, tcur));
                // output.flush();

                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringbuilder = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null) {
                    stringbuilder.append(line);
                }
                jsonContent = stringbuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
                throw new GetRateException(e.getMessage());
            }

            return getRateFromJSON(jsonContent);
        }
    }

    private static double getRateFromJSON(String JSON) throws JSONException {
        Log.w("ExchangeRateModel jsonContent is", JSON);
        JSONObject jsonObject = new JSONObject(JSON);
        return jsonObject.getDouble("rate");
    }

    static String getTarget(String original, double rate) {
        double originalValue;
        try {
            originalValue = Double.parseDouble(original);
            return String.valueOf(originalValue * rate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Number Format Error!";
        }
    }
}
