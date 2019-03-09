package com.zte.zshop.exception;

/**
 * Author:hellboy
 * Date:2018-11-16 16:16
 * Description:<描述>
 */
public class LoginErrorException extends Exception {

    public LoginErrorException() {
        super();
    }

    public LoginErrorException(String message) {
        super(message);
    }

    public LoginErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginErrorException(Throwable cause) {
        super(cause);
    }

    protected LoginErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
