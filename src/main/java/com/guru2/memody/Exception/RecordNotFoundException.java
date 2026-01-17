package com.guru2.memody.Exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super(message);
    }
    public RecordNotFoundException() {super("Record not found");}
}
