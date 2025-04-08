package com.tienda.accesorios.accesoriostiendaapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Sirve archivos desde la carpeta uploads/ en el sistema de archivos
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}

