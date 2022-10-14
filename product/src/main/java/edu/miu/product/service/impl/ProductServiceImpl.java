package edu.miu.product.service.impl;

import edu.miu.product.entity.Product;
import edu.miu.product.repository.ProductRepo;
import edu.miu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;
    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepo.findProductById(id);
    }

    @Override
    public void save(Product p) {
        productRepo.save(p);
    }

    @Override
    public void update(Long id, Product product) {
        product.setId(id);
        productRepo.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public void updateQuanitiy(Long id, Integer quantity) {
        Product p = productRepo.getById(id);
        p.setAvailableUnit(quantity);
        productRepo.save(p);
    }
}
