package com.richiepreece.proofpoint.filesystem.exceptions;

/**
 * Created by Richie on 9/29/2016.
 */
public class PathAlreadyExistsException extends Exception {
    public PathAlreadyExistsException (String message) {
        super(message);
    }
}
