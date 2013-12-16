package com.metagx.foundation.exception;

/**
 * Created by Adam on 12/15/13.
 */
public class SuperClassDidNotImplementException extends RuntimeException {
    public String msg;

    public SuperClassDidNotImplementException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
