package com.example.dnd;

public class InvalidIntegerException extends Exception {

    /**
     * Constructor.
     * @param message Message about the wrong integer entered by the user
     */
    public InvalidIntegerException(String message) {
        super(message);
    }


    /**
     * Default constructor
     */
    public InvalidIntegerException() { super(); }
}
