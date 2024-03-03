package com.udacity.jwdnd.course1.cloudstorage;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class CloudStorageApplication {

    static ConfigurableApplicationContext ct;

    public static void main(String[] args) {
        System.out.println("main function");
        ct = SpringApplication.run(CloudStorageApplication.class, args);
    }

    public static void stop() {
        System.out.println("stop function");
        SpringApplication.exit(ct, () -> 0);
        ct.close();
    }

}
