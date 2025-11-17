package project.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import project.inventory.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}