package edu.mu.order.DTO;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private double price;
    private int availableUnit;
}
