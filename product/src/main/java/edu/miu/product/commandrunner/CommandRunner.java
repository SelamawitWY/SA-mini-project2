package edu.miu.product.commandrunner;



import edu.miu.product.entity.Category;
import edu.miu.product.entity.Product;
import edu.miu.product.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CommandRunner implements CommandLineRunner {
    @Autowired
    private ProductRepo productRepo;



    @Override
    public void run(String... args) throws Exception {

        Product product = new Product("Iphone","Apple", Category.ELECTRONICS,54,1000);
        Product product2 = new Product("Galaxy","Samsung", Category.ELECTRONICS,53,2000);
        Product product3 = new Product("Rubix Cube","Toy Store", Category.TOYS,55,200);

        productRepo.save(product);
        productRepo.save(product2);
        productRepo.save(product3);
    }
}
