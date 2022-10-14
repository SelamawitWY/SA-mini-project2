package edu.miu.creditcard.service.impl;


import edu.miu.creditcard.model.PaymentRequest;
import edu.miu.creditcard.service.CreditCardService;
import org.springframework.stereotype.Service;

@Service
public class CreditCardImpl implements CreditCardService {

    @Override
    public String pay(PaymentRequest paymentRequest) {
        System.out.println("From credit-card service - successfully made payment for account " + paymentRequest.getAccountId() + " and order " + paymentRequest.getOrderId());
        return "OrderID " + paymentRequest.getOrderId() + " And AccountId " + paymentRequest.getAccountId() + "Payment has been successful through CreditCard Service" ;
    }
}
