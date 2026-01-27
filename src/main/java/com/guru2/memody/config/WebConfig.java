package com.guru2.memody.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 이미지 저장 경로
    @Value("${image_storage}")
    private String imageStoragePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String resourceLocation = imageStoragePath;

        // 경로 보정 로직 (file: 접두어 및 끝 슬래시 처리)
        if (!resourceLocation.startsWith("file:")) {
            if (resourceLocation.startsWith("/")) {
                resourceLocation = "file://" + resourceLocation;
            } else {
                resourceLocation = "file:///" + resourceLocation;
            }
        }

        if (!resourceLocation.endsWith("/")) {
            resourceLocation += "/";
        }

        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations(resourceLocation);
    }
}