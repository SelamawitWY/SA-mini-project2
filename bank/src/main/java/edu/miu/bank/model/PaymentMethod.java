package edu.miu.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {
    private PaymentType paymentType;

    private String routingNumber;

    private String bankAccount;

    private String ccAccount;

    private String CCV;

    private String paypalAccount;
}
