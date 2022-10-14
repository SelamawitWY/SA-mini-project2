package edu.mu.order.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPaymentRequest {
    private Integer orderId;
    private PaymentType paymentType;
}
