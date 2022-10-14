package edu.mu.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer productId;
    private Integer quantity;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "OWNER_ID")
//    private Order owner;
}
