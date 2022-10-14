package edu.miu.paypal.controller;

import edu.miu.paypal.model.PaymentRequest;
import edu.miu.paypal.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaypalController {
    @Autowired
    PaypalService paypalService;

    @PostMapping
    public String pay(@RequestBody PaymentRequest paymentRequest){
        return paypalService.pay(paymentRequest);
    }
}
