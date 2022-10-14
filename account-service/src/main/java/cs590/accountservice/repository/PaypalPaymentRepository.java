package cs590.accountservice.repository;


import cs590.accountservice.entity.PaypalPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaypalPaymentRepository extends JpaRepository<PaypalPayment, Integer> {
}
