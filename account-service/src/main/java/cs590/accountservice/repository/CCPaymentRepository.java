package cs590.accountservice.repository;

import cs590.accountservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CCPaymentRepository extends JpaRepository<PaymentMethod, Integer> {
}
