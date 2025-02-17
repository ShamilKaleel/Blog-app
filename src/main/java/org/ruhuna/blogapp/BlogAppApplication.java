package org.ruhuna.blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
@SpringBootApplication
public class BlogAppApplication {


    public static void main(String[] args) {
//        Dotenv dotenv = Dotenv.configure()
//                .ignoreIfMissing()
//                .load();
//
//        dotenv.entries().forEach(entry -> {
//            System.setProperty(entry.getKey(), entry.getValue());
//        });
//
//        System.out.println("Environment variables loaded successfully.");
        SpringApplication.run(BlogAppApplication.class, args);
    }

}
