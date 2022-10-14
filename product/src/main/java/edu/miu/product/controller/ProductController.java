package edu.miu.product.controller;

import edu.miu.product.entity.Product;
import edu.miu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/getAll")
    public List<Product> findAll(){
      return   productService.findAll();
    }
    @GetMapping("/getById/{id}")
    public Product findById(@PathVariable Long id){
               return productService.findById(id);
    }
    @PostMapping("/save")
    public void save(@RequestBody Product p){
        productService.save(p);
    }
    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody Product product){
        productService.update(id,product);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        productService.deleteById(id);
    }
    @PutMapping("/updateQuantity/{productId}/{quantity}")
    public void updateQuantity(@PathVariable Long productId, @PathVariable Integer quantity){
        productService.updateQuanitiy(productId,quantity);
    }
}
