package com.anabelen.dynamodb_api.controller; // (1)

import java.util.List; // (2)

import org.springframework.http.HttpStatus; // (3)
import org.springframework.http.ResponseEntity; // (4)
import org.springframework.web.bind.annotation.DeleteMapping; // (5)
import org.springframework.web.bind.annotation.GetMapping; // (6)
import org.springframework.web.bind.annotation.PathVariable; // (7)
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anabelen.dynamodb_api.model.Product;
import com.anabelen.dynamodb_api.repository.ProductRepository;

@RestController // (8)
@RequestMapping("/products") // (9)
public class ProductController {

    private final ProductRepository productRepository; // (10)

    // Constructor para la inyección de dependencias de Spring (11)
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Endpoint para CREAR un nuevo producto (POST /products) (12)
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) { // (13)
        Product savedProduct = productRepository.save(product); // Llama al método save del repositorio
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED); // Devuelve el producto guardado y un status 201
    }

    // Endpoint para LEER un producto por su ID (GET /products/{productId}) (14)
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) { // (15)
        // Utiliza Optional para manejar si el producto se encuentra o no
        return productRepository.findById(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK)) // Si se encuentra, devuelve 200 OK
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Si no se encuentra, devuelve 404 Not Found
    }

    // Endpoint para LEER todos los productos (GET /products) (16)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll(); // Llama al método findAll del repositorio
        return new ResponseEntity<>(products, HttpStatus.OK); // Devuelve la lista de productos y un status 200 OK
    }

    // Endpoint para ACTUALIZAR un producto existente (PUT /products/{productId}) (17)
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody Product productDetails) {
        Product updatedProduct = productRepository.update(productId, productDetails); // Llama al método update del repositorio
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK); // Si se actualiza, devuelve 200 OK
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra, devuelve 404 Not Found
    }

    // Endpoint para ELIMINAR un producto por su ID (DELETE /products/{productId}) (18)
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        productRepository.deleteById(productId); // Llama al método deleteById del repositorio
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Devuelve 204 No Content
    }
}