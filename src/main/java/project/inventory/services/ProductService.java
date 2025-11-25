package project.inventory.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.inventory.domain.product.Product;
import project.inventory.repositories.ProductRepository;
import project.inventory.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
    
    @Autowired
    ProductRepository repository;

    public Product insertProduct(Product prod) {
        return repository.save(prod);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> prod = repository.findById(id);
        return prod.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void deleteProduct(Product prod) {
        repository.delete(prod);
    }

}