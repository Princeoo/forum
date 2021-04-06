package com.princeoo.forum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            registry.addResourceHandler("/productPic/**").
                    addResourceLocations("file:D:/uploadBaseDir/productPic/");
        }else{//linux和mac系统
            registry.addResourceHandler("/productPic/**").
                    addResourceLocations("file:/Users/princegg/Desktop/");
        }
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}