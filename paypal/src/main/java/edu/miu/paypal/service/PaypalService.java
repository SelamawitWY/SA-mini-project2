package edu.miu.paypal.service;

import edu.miu.paypal.model.PaymentRequest;

public interface PaypalService {
    String pay(PaymentRequest paymentRequest);
}
