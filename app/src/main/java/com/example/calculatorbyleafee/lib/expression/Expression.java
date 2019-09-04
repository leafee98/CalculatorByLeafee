package com.example.calculatorbyleafee.lib.expression;

import java.util.ArrayList;
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

    public static Lqueue<String> str2queue(String str) {
        str = str.replaceAll(" ", "");
        Lqueue<String> que = new Lqueue<String>();
        int j = 0, i;
        for (i = 0; i < str.length(); ++i) {
            if ((str.charAt(i) < '0' || str.charAt(i) > '9') && str.charAt(i) != '.') {
                if (j != i)
                    // push number every another symbol
                    que.push(str.substring(j, i));

                if ((i == 0 && str.charAt(i) == '-') || (str.charAt(i) == '-' && (str.charAt(i - 1) < '0' || str.charAt(i - 1) > '9')))
                    // if it is a negative number
                    // if the first number is negative || other number is negative
                    j = i;
                else {
                    // push the present symbol
                    // todo if it is an alphabet, will handle as operator
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
        ArrayList<String> tx = new ArrayList<String>();
        // get the data from exp
        while (!exp.isempty()) {
            try {
                tx.add(exp.pop());
            } catch (EmptyContainerException e) {
                System.out.println("Impossible Exception");
                e.printStackTrace();
            }
        }
        for (int i = 0; i < tx.size(); ++i) {
            // if it is an operator
            if (tx.get(i).equals("+") || tx.get(i).equals("-")
                || tx.get(i).equals("*") || tx.get(i).equals("/"))
                    ++opCount;
            
            // if it can be translate to an float number, increase the numCount;
            try {
                Double.parseDouble(tx.get(i));
                ++numCount;
            } catch (NumberFormatException e) { }

            // push the data back to exp
            exp.push(tx.get(i));
        }
        if (numCount - 1 !=  opCount)
            throw new ErrorExpressionException("Count of operator mismatch numbers");


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
                } else if (t.equals("(")) {
                    temp.push(t);
                } else if (t.equals("+") || t.equals("-")) {
                    if (!temp.isempty() && (temp.top().equals("*") || temp.top().equals("/"))) {
                        while (!temp.isempty()) {
                            if (temp.top().equals("("))
                                break;
                            result.push(temp.pop());
                        }
                    }
                    temp.push(t);
                } else if (t.equals("*") || t.equals("/")) {
                    temp.push(t);
                } else { 
                // if it is a number
                    result.push(t);
                }

                if (verbose)
                    System.out.println("stack is:" + temp.toString(" / ") + "; output is:" + result.toString(" "));
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
                System.out.println("Something unknown is error!");
                e1.printStackTrace();
            }
        } finally {
            if (verbose) 
                System.out.println("stack is:" + temp.toString() + "; output is:" + result.toString(" "));
        }

        return result;
    }

    public static double calculate(String exp) throws ErrorExpressionException {
        return Expression.calculate(exp, false);
    }
    public static double calculate(String exp, boolean verbose) throws ErrorExpressionException {
        return Expression.calculate(Expression.infix2suffix(Expression.str2queue(exp), verbose), verbose);
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
                // in the abstract, there wouldn't has any EmptyContainerExcetpion,
                // because it has been judged before while
                e.printStackTrace();
            } catch (NumberFormatException e) {
                try {
                    if (que.front().equals("+")) {
                        stk.push(Double.parseDouble(stk.pop()) + Double.parseDouble(stk.pop()) + "");
                    } else if (que.front().equals("-")) {
                        stk.push(Double.parseDouble(stk.pop()) - Double.parseDouble(stk.pop()) + "");
                    } else if (que.front().equals("*")) {
                        stk.push(Double.parseDouble(stk.pop()) * Double.parseDouble(stk.pop()) + "");
                    } else if (que.front().equals("/")) {
                        stk.push(Double.parseDouble(stk.pop()) / Double.parseDouble(stk.pop()) + "");
                    } else {
                        throw new ErrorExpressionException("unsupported symbol");
                    }
                    que.pop();
                } catch (EmptyContainerException f) {
                    throw new ErrorExpressionException("Unknown expression error!");
                }
            }
            if (verbose)
                System.out.println("stack:" + stk.toString() + ", queue:" + que.toString(" "));
        }

        // get the calculate result from the stack;
        try {
            return Double.parseDouble(stk.pop());
        } catch (EmptyContainerException e) {
            System.out.println("Something unknown error! While getting the result.");
            e.printStackTrace();
            return Double.MIN_NORMAL;
        } catch (NumberFormatException e) {
            System.out.println("Something unknown error! While getting the result.");
            e.printStackTrace();
            return Double.MIN_NORMAL;
        }
    }
}