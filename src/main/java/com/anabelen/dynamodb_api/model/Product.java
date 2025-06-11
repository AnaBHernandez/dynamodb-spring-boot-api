package com.anabelen.dynamodb_api.model; // ¡Asegúrate que este paquete es correcto para tu proyecto!

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "anabelen-products") // ¡IMPORTANTE!: Este nombre DEBE coincidir con el nombre de tu tabla en DynamoDB
public class Product {

    @DynamoDBHashKey(attributeName = "productId") // Define la clave primaria (Partition Key). El nombre del atributo en DynamoDB.
    private String productId; // Campo Java que mapea a la clave de partición

    @DynamoDBAttribute(attributeName = "name") // Define un atributo regular. El nombre del atributo en DynamoDB.
    private String name; // Campo Java que mapea al atributo 'name'

    @DynamoDBAttribute(attributeName = "description")
    private String description;

    @DynamoDBAttribute(attributeName = "price")
    private Double price;

    // --- Constructores ---
    // Constructor vacío: ES CRUCIAL para que DynamoDBMapper pueda instanciar objetos
    public Product() {
    }

    // Constructor con todos los argumentos: Útil para crear objetos fácilmente
    public Product(String productId, String name, String description, Double price) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // --- Getters y Setters ---
    // Son necesarios para que DynamoDBMapper pueda leer y escribir los valores de los atributos
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // --- Método toString (Opcional, pero útil para depuración) ---
    @Override
    public String toString() {
        return "Product{" +
               "productId='" + productId + '\'' +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", price=" + price +
               '}';
    }
}