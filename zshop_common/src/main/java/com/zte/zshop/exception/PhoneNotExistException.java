package com.zte.zshop.exception;

/**
 * Author:hellboy
 * Date:2018-11-19 15:58
 * Description:<描述>
 */
public class PhoneNotExistException extends Exception {


    public PhoneNotExistException() {
        super();
    }

    public PhoneNotExistException(String message) {
        super(message);
    }

    public PhoneNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNotExistException(Throwable cause) {
        super(cause);
    }

    protected PhoneNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
