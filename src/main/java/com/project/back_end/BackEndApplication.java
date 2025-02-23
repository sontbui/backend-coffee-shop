package com.project.back_end;

import com.project.back_end.services.MongoDBConnectionService;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.project")
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
            ApplicationContext context =  SpringApplication.run(BackEndApplication.class, args);
			org.springframework.core.env.Environment env = context.getEnvironment();
			String port = env.getProperty("server.port");
			System.out.println("Application is running in " + port);
        } else {
            // If the connection fails, exit the application
            System.exit(1);
        }
    }
}
