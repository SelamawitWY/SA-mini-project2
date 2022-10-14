package edu.miu.creditcard.controller;

import edu.miu.creditcard.model.PaymentRequest;
import edu.miu.creditcard.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CreditCardController {
    @Autowired
    CreditCardService creditCardService;

    @PostMapping
    public String pay(@RequestBody PaymentRequest paymentRequest){
        return creditCardService.pay(paymentRequest);
    }
}

