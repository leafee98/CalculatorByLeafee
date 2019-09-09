package com.example.calculatorbyleafee.lib.expression;

import android.util.Log;

import java.util.ArrayList;
import java.math.*;
import com.example.calculatorbyleafee.lib.datastructure.*;

public class Expression {
    public static void main(String[] args) {
        // Lqueue<String> test = str2queue("-1+-2.1");              // finished SUPPORT negative number IN str2queue
        // Lqueue<String> test = str2queue("1.1+1");                // finished SUPPORT float number IN str2queue
        // Lqueue<String> test = str2queue("1 + 1");                // finished SUPPORT space in expression IN str2queue
        // Lqueue<String> test = str2queue("1++(2)*(3+4)*5+6");     // finished SUPPORT too much or few operators error
        // Lqueue<String> test = str2queue("1)+(2)*(3+4)*5+6");     // finished CHECK the bracket matching IN infix2suffix
        // Lqueue<String> test = str2queue("1(+(2)*(3+4)*5+6");     // same as above
        Lqueue<String> test = str2queue("1 + (2) * (3 + 4) * 5 + 6");
        try {
            System.out.println(calculate(infix2suffix(test, true), true));
        } catch (Exception e) {
            System.out.println("The expression is error!");
            e.printStackTrace();
        }
    }

    public static String pretreatment(String exp) {
        return exp.replaceAll("tan", "t").replaceAll("mod", "%")
                .replaceAll("sin", "s").replaceAll("cos", "c");
    }

    private static boolean isOperator(String op) {
        if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")
                || op.equals("%") || op.equals("^")
                || op.equals("s") || op.equals("c") || op.equals("t")) {
            return true;
        } else {
            return false;
        }
    }
    private static int priority(String op) {
        if (op.equals("+") || op.equals("-")) {
            return 1;
        } else if (op.equals("*") || op.equals("/") || op.equals("%")) {
            return 2;
        } else if (op.equals("^")) {
            return 3;
        } else if (op.equals("s") || op.equals("c") || op.equals("t")) {
            return 9;
        } else {
            return -1;
        }
    }

    public static Lqueue<String> str2queue(String str) {
        str = str.replaceAll(" ", "");
        Lqueue<String> que = new Lqueue<String>();
        int j = 0, i;
        for (i = 0; i < str.length(); ++i) {
            if ((str.charAt(i) < '0' || str.charAt(i) > '9') && str.charAt(i) != '.') {
                if (j != i)
                    // push number every another symbol
                    que.push(str.substring(j, i));

                // if it is a negative number
                // if the first number is negative || other number is negative
                if ((i == 0 && str.charAt(i) == '-') || (str.charAt(i) == '-' && (str.charAt(i - 1) < '0' || str.charAt(i - 1) > '9')))
                    j = i;

                // push the present symbol
                // if it is an alphabet, will handle as operator
                else {
                    que.push("" + str.charAt(i));
                    j = i + 1;
                }
            }
        }
        if (!(str.charAt(str.length() - 1) < '0' || str.charAt(str.length() - 1) > '9')) {
            que.push(str.substring(j, str.length()));
        }
        return que;
    }

    public static Lqueue<String> infix2suffix(Lqueue<String> exp) throws ErrorExpressionException {
        return Expression.infix2suffix(exp, false);
    }

    public static Lqueue<String> infix2suffix(Lqueue<String> exp, boolean verbose) throws ErrorExpressionException {
        Lstack<String> temp = new Lstack<String>();
        Lqueue<String> result = new Lqueue<String>();

        // check if there is too much more symbols than numbers
        int opCount = 0, numCount = 0;

        // get the data from exp to tx
        ArrayList<String> tx = new ArrayList<String>();
        while (!exp.isempty()) {
            try {
                tx.add(exp.pop());
            } catch (EmptyContainerException e) {
                Log.e("infix2suffix", "Impossible Exception while infix to suffix");
                e.printStackTrace();
            }
        }

        for (int i = 0; i < tx.size(); ++i) {
            // if it can be translate to an float number, increase the numCount;
            try {
                Double.parseDouble(tx.get(i));
                ++numCount;
            } catch (NumberFormatException e) {
                // else it is an operator, and not single monocular operator, increase the opCount
                if (!(tx.get(i).equals("s") || tx.get(i).equals("c") || tx.get(i).equals("t")
                        || tx.get(i).equals("(") || tx.get(i).equals(")")))
                    ++opCount;
            }

            // push the data back to exp
            exp.push(tx.get(i));
        }

        if (numCount - 1 !=  opCount) {
            Log.e("infix2suffix, check operator numbers",
                    "numCount=" + numCount + ", opCount=" + opCount);
            throw new ErrorExpressionException("Count of operator mismatch numbers");
        }


        // translate infix expression to suffix expression
        String t;
        try {
            while (true) {
                t = exp.pop();

                // if is a right parenthesis, pop stack until left parenthesis
                if (t.equals(")")) {
                    try {
                        String t1 = temp.pop();
                        while (!t1.equals("(")) {
                            result.push(t1);
                            t1 = temp.pop();
                        }
                    } catch (EmptyContainerException e) {
                        throw new ErrorExpressionException("Mismatched Parenthesis");
                    }

                // if is a left parenthesis, just push it into operator stack
                } else if (t.equals("(")) {
                    temp.push(t);

                // !!!!!!! if we get an operator,
                // **pop all operators in stack
                // until an operator's priority is lower, or meet left parenthesis**,
                // In another word,
                // the priority of operator in stack must be increasing except parenthesis
                } else if (isOperator(t)) {
                    while (!temp.isempty() && !temp.top().equals("(")
                            && priority(temp.top()) >= priority(t)) {
                        result.push(temp.pop());
                    }
                    temp.push(t);

                // if it is a number
                } else {
                    result.push(t);
                }

                if (verbose)
                    Log.d("infix2suffix, step by step log",
                            "stack is:" + temp.toString(" / ") + "; output is:"
                            + result.toString(" "));
            }

        // if catch the end of the queue, we need to pop the stack into queue
        // until the stack is empty
        } catch (EmptyContainerException e) { 
            try {
                while (!temp.isempty()) {
                    if (temp.top().equals("("))
                        throw new ErrorExpressionException("Mismatched Parentheses");
                    result.push(temp.pop());
                }

            // in the abstract, there wouldn't has any EmptyContainerException,
            // but wrote this for the syntax
            } catch (EmptyContainerException e1) {
                Log.e("infix2suffix, pop all operator remained",
                        "EmptyContainerException, but will never happen.");
                e1.printStackTrace();
            }
        } finally {
            if (verbose) 
                Log.d("the result after infix2suffix",
                        "stack is:" + temp.toString() + "; output is:"
                         + result.toString(" "));
        }

        return result;
    }

    public static double calculate(String exp) throws ErrorExpressionException {
        return Expression.calculate(exp, false);
    }
    public static double calculate(String exp, boolean verbose) throws ErrorExpressionException {
        return Expression.calculate(Expression.infix2suffix(Expression.str2queue(pretreatment(exp)), verbose), verbose);
    }
    public static double calculate(Lqueue<String> que) throws ErrorExpressionException {
        return Expression.calculate(que, false);
    }
    public static double calculate(Lqueue<String> que, boolean verbose) throws ErrorExpressionException {
        Lstack<String> stk = new Lstack<String>();
        while (!que.isempty()) {
            try {
                Double.parseDouble(que.front());
                stk.push(que.pop());
            } catch (EmptyContainerException e) {
                // in the abstract, there wouldn't has any EmptyContainerException,
                // because it has been judged before while
                e.printStackTrace();

            // if the front is operator
            } catch (NumberFormatException e) {
                try {
                    if (que.front().equals("s")) {
                        stk.push(Math.sin(Double.parseDouble(stk.pop())) + "");
                    } else if (que.front().equals("c")) {
                        stk.push(Math.cos(Double.parseDouble(stk.pop())) + "");
                    } else if (que.front().equals("t")) {
                        stk.push(Math.tan(Double.parseDouble(stk.pop())) + "");
                    } else {
                        // take care of the calculate order, if it's c/b/a, in number stack from top to bottom,
                        // the operator is --, it should be 'b-c' and then 'a-(b-c)'.
                        // PS: the original expression is "a-(b-c)"
                        Double opNumb = Double.parseDouble(stk.pop());
                        Double opNuma = Double.parseDouble(stk.pop());
                        if (que.front().equals("+")) {
                            stk.push(opNuma + opNumb + "");
                        } else if (que.front().equals("-")) {
                            stk.push(opNuma - opNumb + "");
                        } else if (que.front().equals("*")) {
                            stk.push(opNuma * opNumb + "");
                        } else if (que.front().equals("/")) {
                            stk.push(opNuma / opNumb + "");
                        } else if (que.front().equals("^")) {
                            stk.push(Math.pow(opNuma, opNumb) + "");
                        } else if (que.front().equals("%")) {
                            stk.push(opNuma % opNumb + "");
                        } else {
                            throw new ErrorExpressionException("unsupported symbol");
                        }
                    }
                    que.pop();
                } catch (EmptyContainerException f) {
                    throw new ErrorExpressionException("Unknown expression error!");
                }
            }
            if (verbose)
                Log.d("calculate, step by step",
                        "stack:" + stk.toString() + ", queue:" + que.toString(" "));
        }

        // get the calculate result from the stack;
        try {
            return Double.parseDouble(stk.pop());
        } catch (EmptyContainerException e) {
            Log.e("calculate, getting result", "but no more element");
            throw new ErrorExpressionException("getting result, but no more element");
        } catch (NumberFormatException e) {
            Log.e("calculate, getting result", "but the result is not a number");
            throw new ErrorExpressionException("getting result, but the result is not a number");
        }
    }
}