package org.example;

public class MissingSaveException extends Exception {
    public MissingSaveException(String errorMessage) {
        super(errorMessage);
    }

//    public MissingSaveException(String errorMessage, Throwable cause,Cou==) {
//        super(errorMessage, cause);
//    }
}
