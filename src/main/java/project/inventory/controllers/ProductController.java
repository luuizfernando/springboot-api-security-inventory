package project.inventory.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import project.inventory.domain.product.Product;
import project.inventory.services.ProductService;


@RestController
@RequestMapping("products")
@Tag(name = "Products")
public class ProductController {
    
    @Autowired
    ProductService service;

    @GetMapping
    @Operation(summary = "Listar produtos")
    @ApiResponse(responseCode = "200", description = "Lista de produtos",
        content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    public ResponseEntity<List<Product>> findAll() {
        List<Product> list = service.getAllProducts();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar produto por ID")
    @ApiResponse(responseCode = "200", description = "Produto",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)))
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = service.getProductById(id);
        return ResponseEntity.ok().body(product);
    }
    
    @PostMapping
    @Operation(summary = "Criar produto")
    @ApiResponse(responseCode = "201", description = "Produto criado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)))
    public ResponseEntity<Product> insertProduct(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do produto",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Product.class),
                examples = @ExampleObject(value = "{\\\"name\\\":\\\"Mouse\\\",\\\"description\\\":\\\"Wireless\\\",\\\"price\\\":99.9,\\\"quantity\\\":10}")))
        Product prod) {
        prod = service.insertProduct(prod);
        return ResponseEntity.status(HttpStatus.CREATED).body(prod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Product product = service.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        service.deleteProduct(product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
