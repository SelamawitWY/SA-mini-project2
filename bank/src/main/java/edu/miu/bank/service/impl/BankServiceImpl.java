package edu.miu.bank.service.impl;

import edu.miu.bank.model.PaymentRequest;
import edu.miu.bank.service.BankService;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

    @Override
    public String pay(PaymentRequest paymentRequest) {
        System.out.println("From bank service - successfully made payment for account " + paymentRequest.getAccountId() + " and order " + paymentRequest.getOrderId());
        return "OrderID " + paymentRequest.getOrderId() + " And AccountId " + paymentRequest.getAccountId() + "Payment has been successful through Bank Service" ;
    }
}
