package com.ecommerce.ecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class Webconfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addRedirectViewController("/docApi/v2/api-docs", "/v2/api-docs");
                registry.addRedirectViewController("/docApi/swagger-resources/configuration/ui",
                        "/swagger-resources/configuration/ui");
                registry.addRedirectViewController("/docApi/swagger-resources/configuration/security",
                        "/swagger-resources/configuration/security");
                registry.addRedirectViewController("/docApi/swagger-resources", "/swagger-resources");
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/docApi/swagger-ui.html**")
                        .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
                registry.addResourceHandler("/docApi/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
            }
        };
    }
}