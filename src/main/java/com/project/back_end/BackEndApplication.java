package com.project.back_end;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;

import javax.crypto.SecretKey;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableMongoRepositories(basePackages = "com.project.back_end.repositories")
public class BackEndApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));
        System.setProperty("MONGO_DATABASE", dotenv.get("MONGO_DATABASE"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));

        String jwtSecret = System.getenv("JWT_SECRET");

        if (jwtSecret == null || jwtSecret.isEmpty()) {
            SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
            String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
            System.out.println("⚠️ New JWT Secret Key (Only for local development): " + base64Key);
        }
        SpringApplication.run(BackEndApplication.class, args);
    }
}
