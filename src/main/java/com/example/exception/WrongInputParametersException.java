package com.example.exception;

import java.io.IOException;

public class WrongInputParametersException extends IOException {
    private static final int WRONG_INPUT_PARAMETERS_CODE = 400;

    WrongInputParametersException() {
        super();
    }

    public int getCode() {
        return WRONG_INPUT_PARAMETERS_CODE;
    }
}
