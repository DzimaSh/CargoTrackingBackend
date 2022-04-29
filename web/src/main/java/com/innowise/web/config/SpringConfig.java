package com.innowise.web.config;

import com.innowise.web.project.About;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:projectParams.properties")
public class SpringConfig {

    @Bean
    public About about() {
        return new About();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
