package edu.miu.product.repository;

import edu.miu.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    Product findProductById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
}
