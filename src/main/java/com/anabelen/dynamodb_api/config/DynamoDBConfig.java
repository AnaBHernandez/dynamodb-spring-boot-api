package com.anabelen.dynamodb_api.config; // ¡Asegúrate que este paquete es correcto para tu proyecto!

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean; // Esta importación puede volverse redundante o se cambia
import org.springframework.context.annotation.Configuration; // <--- ¡NUEVA IMPORTACIÓN!

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;


@Configuration
public class DynamoDBConfig {

    @Value("${aws.dynamodb.endpoint}")
    private String dynamoDbEndpoint;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.sessionToken}") // <--- ¡NUEVA VARIABLE PARA EL TOKEN DE SESIÓN!
    private String awsSessionToken; // <-- Declaración de la nueva variable

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(dynamoDbEndpoint, awsRegion))
                .withCredentials(
                        // Usar BasicSessionCredentials para las credenciales temporales
                        new AWSStaticCredentialsProvider(
                                new BasicSessionCredentials(awsAccessKey, awsSecretKey, awsSessionToken))) // <--- ¡CAMBIO AQUÍ: Ahora se pasan 3 argumentos!
                .build();
    }
}