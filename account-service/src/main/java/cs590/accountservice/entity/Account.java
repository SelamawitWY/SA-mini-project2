package cs590.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address shippingAddress;

    private PaymentType preferredPayment;
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<PaymentMethod> paymentMethods;

    public Account(String firstName, String lastName, String email, String password, Address shippingAddress, PaymentType preferredPayment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.shippingAddress = shippingAddress;
        this.preferredPayment = preferredPayment;
    }
}
