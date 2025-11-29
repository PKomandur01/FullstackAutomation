//It is the main entry point of your Spring Boot application.

//Meaning:

//When you hit run, this is the file that actually starts the server

//It boots up Spring Boot

//It sets up all the controllers, services, repositories

//It starts your database connection

//It starts your web server on port 8080

//It makes your API routes available
package com.example.automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
