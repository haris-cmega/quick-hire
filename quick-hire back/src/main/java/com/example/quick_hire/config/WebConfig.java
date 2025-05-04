// src/main/java/com/example/quick_hire/config/WebConfig.java
package com.example.quick_hire.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve files under the 'public' directory at URLs under /public/**
        registry.addResourceHandler("/public/**")
                .addResourceLocations("file:public/");
    }
}
