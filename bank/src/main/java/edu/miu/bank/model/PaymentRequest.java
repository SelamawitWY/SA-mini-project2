package edu.miu.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    Integer accountId;
    Integer orderId;
    PaymentMethod paymentMethod;
}
