package com.hulahoop.blueback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                // 🔥 브라우저에서 실제로 접속하는 Origin 들
                .allowedOrigins(
                        "http://localhost:3001", // Docker: blue-front
                        "http://localhost:5173", // Vite dev
                        "http://localhost:3000", // 기타 로컬
                        "http://43.201.205.26:5173" // EC2 Blue-front
                )
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization");
    }
}
