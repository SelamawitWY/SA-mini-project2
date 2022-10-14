package cs590.accountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Bank-P")
public class BankPayment extends PaymentMethod{
    private String routingNumber;

    private String bankAccount;
}
