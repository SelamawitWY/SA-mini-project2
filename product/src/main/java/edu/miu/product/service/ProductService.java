package edu.miu.product.service;

import edu.miu.product.entity.Product;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductService {
     List<Product> findAll();
     Product findById(Long id);
     void save(Product p);
     void update(Long id, Product product);
     void deleteById(Long id);
      void updateQuanitiy( Long id, Integer quantity);



     }
