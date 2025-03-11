package com.project.back_end;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;

import javax.crypto.SecretKey;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableMongoRepositories(basePackages = "com.project.back_end.repositories")
public class BackEndApplication {

    @Value("${JWT_SECRET}")
    private static String jwtSecret = System.getenv("JWT_SECRET");
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        jwtSecret = dotenv.get("JWT_SECRET");
        if (jwtSecret == null) {
            System.out.println("⚠️ JWT_SECRET is not set in .env file. Please set it to a random string.");
            System.exit(1);
        }
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("⚠️ New JWT Secret Key (Only for local development): " + base64Key);
        SpringApplication.run(BackEndApplication.class, args);
    }
}
