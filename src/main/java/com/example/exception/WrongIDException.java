package com.example.exception;

public class WrongIDException extends Exception {
    private static final int WRONG_ID_EXCEPTION_CODE = 400;

    public WrongIDException() {
        super();
    }

    public int getCode() {
        return WRONG_ID_EXCEPTION_CODE;
    }
}
