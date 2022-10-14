package cs590.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String country;

    private String state;

    private String city;

    private String zipcode;

    private String street;

    @JsonIgnore
    @OneToOne(mappedBy = "shippingAddress")
    private Account owner;

    public Address(String country, String state, String city, String zipcode, String street) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}

