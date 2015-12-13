package com.ibm.spe.comparetool4.compare;

/**
 * Created by luis on 2015/2/7.
 */
public class CompareException extends Exception {
    public CompareException() {
        super();
    }

    public CompareException(String message) {
        super(message);
    }

    public CompareException(Throwable cause) {
        super(cause);
    }
}
