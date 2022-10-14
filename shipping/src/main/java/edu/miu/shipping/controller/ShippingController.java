package edu.miu.shipping.controller;

import edu.miu.shipping.model.ShippingAddress;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {

    @PostMapping
    public Boolean ship(@RequestBody ShippingAddress shippingAddress){
        System.out.println("Order has been shipped to " + shippingAddress);
        return true;
    }

}
