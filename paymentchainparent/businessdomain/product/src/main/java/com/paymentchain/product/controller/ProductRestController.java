package com.paymentchain.product.controller;

import com.paymentchain.product.entities.Product;
import com.paymentchain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    private ProductRepository productRepsitory;

    @GetMapping()
    public List<Product> getAllProducts() {
        return productRepsitory.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepsitory.findById(id).get();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product input) {
       Product product = productRepsitory.save(input);
       return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody Product input) {
        Product product = productRepsitory.save(input);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productRepsitory.findById(id);
        product.ifPresent(value -> productRepsitory.delete(value));
        return ResponseEntity.ok().build();
    }
}
