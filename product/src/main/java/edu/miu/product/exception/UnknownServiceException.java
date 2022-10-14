package edu.miu.product.exception;

public class UnknownServiceException extends RuntimeException {
    public UnknownServiceException() {
        super();
    }
    public UnknownServiceException(String message) {
        super(message);
    }
}
