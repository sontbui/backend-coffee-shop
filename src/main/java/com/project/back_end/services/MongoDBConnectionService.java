package com.project.back_end.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MongoDBConnectionService {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    public boolean checkConnection() {
        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase databases = mongoClient.getDatabase(databaseName);
            if (databases != null) {
    
                System.out.println("Successfully connected to the database: " + databaseName);
                MongoDatabase database = mongoClient.getDatabase(databaseName);
                MongoIterable<String> collections = database.listCollectionNames();
                System.out.println("\nCollections in database '" + databaseName + "':");
                for (String collectionName : collections) {
                    System.out.println("- " + collectionName);
                }

                return true;
            } else {
                System.out.println("Failed to connect to the database: " + databaseName);
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to connect to the database: " + e.getMessage());
            return false;
        }
    }
}