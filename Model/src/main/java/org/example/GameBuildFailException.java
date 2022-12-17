package org.example;

public class GameBuildFailException extends Exception {

    public GameBuildFailException(String errorMessage) {
        super(errorMessage);
    }
    public GameBuildFailException(String errorMessage,Throwable cause) {
        super(errorMessage,cause);
    }
}
