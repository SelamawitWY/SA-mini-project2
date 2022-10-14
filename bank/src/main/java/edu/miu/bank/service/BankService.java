package edu.miu.bank.service;


import edu.miu.bank.model.PaymentRequest;

public interface BankService {
    String pay(PaymentRequest paymentRequest);
}
