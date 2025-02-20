package com.project.back_end;

import com.project.back_end.service.MongoDBConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BackEndApplication {

    @Autowired
    private MongoDBConnectionService mongoDBConnectionService;

    public static void main(String[] args) {
        // Create a temporary application context to get the MongoDBConnectionService bean
        ApplicationContext tempContext = SpringApplication.run(BackEndApplication.class, new String[] {"--spring.main.web-application-type=none"});
        MongoDBConnectionService mongoDBConnectionService = tempContext.getBean(MongoDBConnectionService.class);

        // Check the MongoDB connection
        if (mongoDBConnectionService.checkConnection()) {
            // If the connection is successful, start the full application context
            SpringApplication.run(BackEndApplication.class, args);
        } else {
            // If the connection fails, exit the application
            System.exit(1);
        }
    }
}
