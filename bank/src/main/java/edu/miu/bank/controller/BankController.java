package edu.miu.bank.controller;


import edu.miu.bank.model.PaymentRequest;
import edu.miu.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BankController {
    @Autowired
    BankService bankService;


    @PostMapping
    public String pay(@RequestBody PaymentRequest paymentRequest){
        return bankService.pay(paymentRequest);
    }

}
