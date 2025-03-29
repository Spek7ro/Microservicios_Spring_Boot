package com.paymentchain.product.controller;

import com.paymentchain.product.entities.Product;
import com.paymentchain.product.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    ProductRepository productRepository;

    @Operation(summary = "Lista de productos")
    @GetMapping()
    public List<Product> list() {
        return productRepository.findAll();
    }

    @Operation(summary = "Obtener un producto por ID")
    @GetMapping("/{id}")
    public Product get(@PathVariable(name = "id") long id) {
        return productRepository.findById(id).get();
    }

    @Operation(summary = "Actualizar un producto")
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long id, @RequestBody Product input) {
        Product find = productRepository.findById(id).get();
        if(find != null){
            find.setCode(input.getCode());
            find.setName(input.getName());
        }
        Product save = productRepository.save(find);
        return ResponseEntity.ok(save);
    }

    @Operation(summary = "Crear un producto")
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Product input) {
        Product save = productRepository.save(input);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto por ID")
    public ResponseEntity<?> delete(
            @Parameter(description = "ID del producto a eliminar", required = true)
            @PathVariable(name = "id") long id) {
        Optional<Product> findById = productRepository.findById(id);
        findById.ifPresent(productRepository::delete);
        return ResponseEntity.ok().build();
    }
}
