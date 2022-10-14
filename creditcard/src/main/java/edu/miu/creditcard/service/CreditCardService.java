package edu.miu.creditcard.service;


import edu.miu.creditcard.model.PaymentRequest;

public interface CreditCardService {
    String pay(PaymentRequest paymentRequest);
}
