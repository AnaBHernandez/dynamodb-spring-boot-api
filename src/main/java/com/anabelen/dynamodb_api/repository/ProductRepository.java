package com.anabelen.dynamodb_api.repository; // ¡Asegúrate que este paquete es correcto!

import java.util.List; // Necesario para interactuar con DynamoDB
import java.util.Optional; // Necesario para la operación findAll

import org.springframework.stereotype.Repository; // Importa tu modelo Product

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper; // Anotación de Spring para repositorios
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.anabelen.dynamodb_api.model.Product; // Para manejar casos donde un producto podría no encontrarse

@Repository // (1)
public class ProductRepository {

    private final DynamoDBMapper dynamoDBMapper; // (2)

    // Constructor para la inyección de dependencias de Spring (3)
    public ProductRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    // Operación CREATE y UPDATE (4)
    public Product save(Product product) {
        dynamoDBMapper.save(product); // El método save() de DynamoDBMapper maneja tanto la creación como la actualización.
                                     // Si un ítem con el mismo productId ya existe, lo actualiza; si no, lo crea.
        return product; // Devuelve el producto guardado
    }

    // Operación READ (por ID) (5)
    public Optional<Product> findById(String productId) {
        // load() busca un ítem por su clave primaria (Partition Key y Sort Key si aplica)
        return Optional.ofNullable(dynamoDBMapper.load(Product.class, productId)); // Envuelve el resultado en Optional para seguridad
    }

    // Operación READ (todos los ítems) (6)
    public List<Product> findAll() {
        // scan() devuelve todos los ítems de la tabla.
        // ¡ADVERTENCIA!: Para tablas grandes, scan() puede ser MUY COSTOSO y lento.
        // En producción, para buscar múltiples ítems, se suelen usar Query Operations con índices.
        return dynamoDBMapper.scan(Product.class, new DynamoDBScanExpression());
    }

    // Operación DELETE (por ID) (7)
    public void deleteById(String productId) {
        // Primero, cargamos el producto para asegurarnos de que existe y tener el objeto completo para borrar.
        Product productToDelete = dynamoDBMapper.load(Product.class, productId);
        if (productToDelete != null) { // Verificamos que el producto existe antes de intentar borrarlo.
            dynamoDBMapper.delete(productToDelete); // Borra el ítem de la tabla.
        }
    }

    // Operación UPDATE (ejemplo explícito) (8)
    public Product update(String productId, Product productDetails) {
        // Cargamos el producto existente por su ID.
        Product existingProduct = dynamoDBMapper.load(Product.class, productId);
        if (existingProduct != null) {
            // Actualizamos los campos del producto existente con los nuevos detalles.
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());

            dynamoDBMapper.save(existingProduct); // Guardamos el objeto actualizado en DynamoDB.
            return existingProduct; // Devolvemos el producto actualizado.
        }
        return null; // Si no se encuentra el producto, devolvemos null (o podríamos lanzar una excepción).
    }
}