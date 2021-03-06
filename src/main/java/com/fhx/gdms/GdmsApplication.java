package com.fhx.gdms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class GdmsApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GdmsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GdmsApplication.class, args);
    }
}

//@SpringBootApplication
//public class GdmsApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(GdmsApplication.class, args);
//    }
//}