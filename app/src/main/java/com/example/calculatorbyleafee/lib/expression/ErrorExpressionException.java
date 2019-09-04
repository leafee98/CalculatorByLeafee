package com.example.calculatorbyleafee.lib.expression;

public class ErrorExpressionException extends Exception {
    private static final long serialVersionUID = 6602273568241636585L;
    public ErrorExpressionException(String message) {
        super(message);
    }
    public ErrorExpressionException() {
        super();
    }
}