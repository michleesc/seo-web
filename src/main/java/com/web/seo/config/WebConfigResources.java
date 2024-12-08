package com.web.seo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigResources implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // file seo
        registry.addResourceHandler("/seo/css/**")
                .addResourceLocations("classpath:/static/seo/css/");
        registry.addResourceHandler("/seo/images/**")
                .addResourceLocations("classpath:/static/seo/images/");
        registry.addResourceHandler("/seo/js/**")
                .addResourceLocations("classpath:/static/seo/js/");
        registry.addResourceHandler("/seo/plugins/**")
                .addResourceLocations("classpath:/static/seo/plugins/");
    }
}
