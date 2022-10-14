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
@DiscriminatorValue("CC-P")
public class CCPayment extends PaymentMethod{
    private String ccAccount;

    private String CCV;
}
