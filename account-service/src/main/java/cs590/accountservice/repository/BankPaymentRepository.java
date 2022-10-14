package cs590.accountservice.repository;

import cs590.accountservice.entity.BankPayment;
import cs590.accountservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankPaymentRepository extends JpaRepository<BankPayment, Integer> {
}
