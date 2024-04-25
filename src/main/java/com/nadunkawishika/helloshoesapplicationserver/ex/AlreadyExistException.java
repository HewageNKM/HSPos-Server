package com.nadunkawishika.helloshoesapplicationserver.ex;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message) {
        super(message);
    }
}
