package com.example.demo.business;

import com.example.demo.dto.RepositoryDTOResponse;
import com.example.demo.dto.exception.NotFound;
import com.example.demo.persistence.entity.Repository;
import com.example.demo.persistence.repository.RepositoryRepository;
import com.example.demo.service.GitHubApiService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RepositoriesService {

    private GitHubApiService gitHubApiService;

    private RepositoryRepository repositoryRepository;

    private RepositoryMapper repositoryMapper;

    @Autowired
    public RepositoriesService(GitHubApiService gitHubApiService, RepositoryRepository repositoryRepository, RepositoryMapper repositoryMapper) {
        this.gitHubApiService = gitHubApiService;
        this.repositoryRepository = repositoryRepository;
        this.repositoryMapper = repositoryMapper;
    }

    public List<RepositoryDTOResponse> getRepositories(String username) {
        List<Map> repositoriesGitHub = gitHubApiService.getRepositories(username);

        return repositoryMapper.mapper(repositoriesGitHub);
    }

    @Transactional
    public Boolean saveFavorite(String repositoryName) {
        try {
            Repository repository = repositoryRepository.findByRepository(repositoryName);
            if (repository == null) {
                repository = new Repository();
                repository.setRepository(repositoryName);
                repository.setCounting(0);
            }

            repository.setCounting(repository.getCounting() + 1);
            repositoryRepository.save(repository);
        } catch (Exception e) {
            log.error("Error on save repository ["+ repositoryName +"]", e);
            return false;
        }

        return true;
    }
}
