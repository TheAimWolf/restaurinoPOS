package com.restaurinoinc.restaurinopos;

public class NotYetPaidException extends Exception{
    public NotYetPaidException() {
        super("Die Rechnung wurde noch nicht bezahlt.");
    }

    /**
     * Public Konstruktor Klasse NotYetPaidExeption
     * @param message Nachricht des Fehlers
     */
    public NotYetPaidException(String message) {
        super(message);
    }
}
