package com.richiepreece.proofpoint.filesystem.exceptions;

/**
 * Created by Richie on 9/29/2016.
 */
public class NotATextFileException extends Exception {
    public NotATextFileException (String message) {
        super(message);
    }
}
