package edu.miu.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String vendor;
    private Category category;
    private int availableUnit;
    private double price;

    public Product(String name, String vendor, Category category, int availableUnit, double price) {
        this.name = name;
        this.vendor = vendor;
        this.category = category;
        this.availableUnit = availableUnit;
        this.price = price;
    }
}
