package edu.miu.payment.controller;

import edu.miu.payment.model.OrderRequest;
import edu.miu.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @PostMapping
    public ResponseEntity<?> checkout(@RequestBody OrderRequest orderRequest){
         String response  =  paymentService.checkout(orderRequest);
        return ResponseEntity.ok().body(response);
    }

}
