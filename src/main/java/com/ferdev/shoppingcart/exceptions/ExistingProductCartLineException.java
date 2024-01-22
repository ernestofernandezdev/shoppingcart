package com.ferdev.shoppingcart.exceptions;

public class ExistingProductCartLineException extends RuntimeException {

    public ExistingProductCartLineException(String message) {
        super(message);
    }

    public ExistingProductCartLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingProductCartLineException(Throwable cause) {
        super(cause);
    }
}
