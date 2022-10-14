package edu.miu.payment.service;

import edu.miu.payment.model.OrderRequest;
import org.springframework.stereotype.Service;


public interface PaymentService {
    String checkout(OrderRequest orderRequest);
}
