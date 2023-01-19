package com.project.team.plice.web.webConfig;

import com.project.team.plice.web.interceptor.BlockCheckInterceptor;
import com.project.team.plice.web.interceptor.LoggerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final LoggerInterceptor loggerInterceptor;
    private final BlockCheckInterceptor blockCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
        registry.addInterceptor(blockCheckInterceptor);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSizePerFile(20 * 1024 * 1024); //20MB
        return commonsMultipartResolver;
    }

    // 허락해줄 경로를 설정해줌. 앞에 file:/// 를 붙여줌
    String PermittedPath = "file:///" +System.getProperty("user.dir") + "/src/main/resources/static/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**") // PermittedPath 경로들/+a
                .addResourceLocations(PermittedPath); // 로컬에 저장된 파일을 읽어 올 root 경로 설정
    }

}