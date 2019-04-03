package com.example.dnd;

public class InvalidStringException extends Exception {

    /**
     * Constructor.
     * @param message Message about the wrong integer entered by the user
     */
    public InvalidStringException(String message) {
        super(message);
    }


    public InvalidStringException(){ super(); }
}
