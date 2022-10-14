package edu.miu.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress {
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String street;
}
