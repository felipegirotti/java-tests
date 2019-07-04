package com.example.demo.config;

import com.example.demo.service.GitHubApiService;
import com.example.demo.service.GitHubApiServiceImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@ConfigurationProperties(prefix = "githubapi")
@Data
public class GitHubApiConfig {

    private String url;

    @Bean
    public GitHubApiService gitHubApi() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(url));

        return new GitHubApiServiceImpl(restTemplate);
    }
}
