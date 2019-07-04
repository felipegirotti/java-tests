package com.example.demo.service;

import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GitHubApiServiceImpl implements GitHubApiService {

    private RestTemplate restTemplate;

    public GitHubApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List getRepositories(String username) {
        return restTemplate.getForObject( "/users/"+ username +"/repos", List.class);
    }
}
