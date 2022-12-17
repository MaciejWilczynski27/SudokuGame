package org.example;

public class DataCorruptException extends Exception {
    public DataCorruptException(String errorMessage) {
        super(errorMessage);
    }
    public DataCorruptException(String errorMessage,Throwable cause) {
        super(errorMessage,cause);
    }
}
