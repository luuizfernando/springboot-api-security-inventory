package project.inventory.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.inventory.domain.product.Product;
import project.inventory.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("products")
public class ProductController {
    
    @Autowired
    ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> list = service.getAllProducts();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = service.getProductById(id);
        return ResponseEntity.ok().body(product);
    }
    
    @PostMapping
    public ResponseEntity<Product> insertProduct(@RequestBody Product prod) {
        prod = service.insertProduct(prod);
        return ResponseEntity.status(HttpStatus.CREATED).body(prod);
    }

}