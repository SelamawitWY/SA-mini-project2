package edu.miu.shipping.exception;

public class UnknownServiceException extends RuntimeException {
    public UnknownServiceException() {
        super();
    }
    public UnknownServiceException(String message) {
        super(message);
    }
}
