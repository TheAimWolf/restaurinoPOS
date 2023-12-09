package com.restaurinoinc.restaurinopos;

public class NotYetPaidException extends Exception{
    public NotYetPaidException() {
        super("Die Rechnung wurde noch nicht bezahlt.");
    }

    public NotYetPaidException(String message) {
        super(message);
    }
}
