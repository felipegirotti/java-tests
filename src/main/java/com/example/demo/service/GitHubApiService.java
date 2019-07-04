package com.example.demo.service;

import java.util.List;
import java.util.Map;

public interface GitHubApiService {

    public List<Map> getRepositories(String username);
}
