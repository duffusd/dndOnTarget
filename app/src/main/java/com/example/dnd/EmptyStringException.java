package com.example.dnd;

public class EmptyStringException extends Exception {

    /**
     * Constructor.
     * @param message Message about the empty string
     */
    public EmptyStringException(String message) {
        super(message);
    }


    /**
     * Default constructor
     */
    public EmptyStringException() {
        super();
    }
}
