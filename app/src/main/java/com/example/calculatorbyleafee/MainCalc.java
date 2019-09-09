package com.example.calculatorbyleafee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculatorbyleafee.lib.expression.ErrorExpressionException;
import com.example.calculatorbyleafee.lib.expression.Expression;

import java.util.Arrays;
import java.util.List;

public class MainCalc extends AppCompatActivity {

    private static class InputHandler implements View.OnClickListener {
        private TextView textView;
        private StringBuilder expression = new StringBuilder();

        public InputHandler(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_numPoint: expression.append('.'); break;
                case R.id.button_num0: expression.append('0'); break;
                case R.id.button_num1: expression.append('1'); break;
                case R.id.button_num2: expression.append('2'); break;
                case R.id.button_num3: expression.append('3'); break;
                case R.id.button_num4: expression.append('4'); break;
                case R.id.button_num5: expression.append('5'); break;
                case R.id.button_num6: expression.append('6'); break;
                case R.id.button_num7: expression.append('7'); break;
                case R.id.button_num8: expression.append('8'); break;
                case R.id.button_num9: expression.append('9'); break;
                case R.id.button_opPlus: expression.append('+'); break;
                case R.id.button_opMinus: expression.append('-'); break;
                case R.id.button_opMulti: expression.append('*'); break;
                case R.id.button_opDiv: expression.append('/'); break;
                case R.id.button_opPow: expression.append('^'); break;
                case R.id.button_opSin: expression.append("sin("); break;
                case R.id.button_opCos: expression.append("cos("); break;
                case R.id.button_opTan: expression.append("tan("); break;
                case R.id.button_opMod: expression.append("mod"); break;
                case R.id.button_opLeftParentheses: expression.append('('); break;
                case R.id.button_opRightParentheses: expression.append(')'); break;

                case R.id.button_clearAll:
                    expression = new StringBuilder();
                    break;

                case R.id.button_backspace:
                    try {
                        expression.deleteCharAt(expression.length() - 1);
                    } catch (StringIndexOutOfBoundsException e) {
                        Log.d("input expression",
                                "backspace while expression zero length");
                    }
                    break;
                case R.id.button_opEqual:
                    try {
                        expression = new StringBuilder(
                                String.format("%t .7f",
                                        Expression.calculate(expression.toString(), true)));
                    } catch (ErrorExpressionException e) {
                        expression = new StringBuilder(e.getMessage());
                    }
                    break;
            }

            textView.setText(expression.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calc);

        List<Integer> buttonsId = Arrays.asList(
                R.id.button_num0, R.id.button_num1, R.id.button_num2, R.id.button_num3,
                R.id.button_num4, R.id.button_num5, R.id.button_num6, R.id.button_num7,
                R.id.button_num8, R.id.button_num9, R.id.button_numPoint,
                R.id.button_opPlus, R.id.button_opMinus, R.id.button_opMulti, R.id.button_opDiv,
                R.id.button_opLeftParentheses, R.id.button_opRightParentheses,
                R.id.button_opPow, R.id.button_opMod, R.id.button_opEqual,
                R.id.button_opSin, R.id.button_opCos, R.id.button_opTan,
                R.id.button_clearAll, R.id.button_backspace
        );

        InputHandler inputHandler = new InputHandler((TextView)findViewById(R.id.textView_expressionShower));
        for (int i = 0; i < buttonsId.size(); ++i) {
            ((Button)findViewById(buttonsId.get(i))).setOnClickListener(inputHandler);
        }

    }
}
