package edu.miu.paypal.service.impl;

import edu.miu.paypal.model.PaymentRequest;
import edu.miu.paypal.service.PaypalService;
import org.springframework.stereotype.Service;

@Service
public class PaypalServiceImpl implements PaypalService {

    @Override
    public String pay(PaymentRequest paymentRequest) {
        System.out.println("From paypal service - successfully made payment for account " + paymentRequest.getAccountId() + " and order " + paymentRequest.getOrderId());
        return "OrderID " + paymentRequest.getOrderId() + " And AccountId " + paymentRequest.getAccountId() + "Payment has been successful through Paypal Service" ;
    }
}
