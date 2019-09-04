package com.example.calculatorbyleafee.lib.datastructure;

public class EmptyContainerException extends Exception {
    private static final long serialVersionUID = 6202378284826546585L;
    public EmptyContainerException(String message) {
        super(message);
    }
    public EmptyContainerException() {
        super();
    }
}