package com.example.demo.business;

import com.example.demo.dto.RepositoryDTOResponse;
import com.example.demo.persistence.entity.Repository;
import com.example.demo.persistence.repository.RepositoryRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RepositoryMapper {

    private RepositoryRepository repositoryRepository;

    @Autowired
    public RepositoryMapper(RepositoryRepository repositoryRepository) {
        this.repositoryRepository = repositoryRepository;
    }

    public List<RepositoryDTOResponse> mapper(List<Map> repositoriesGitHub) {
        List names = repositoriesGitHub.stream().map(this::getFullName).collect(Collectors.toList());
        List<Repository> repositories = repositoryRepository.findByRepositoryIn(names);

        return repositoriesGitHub.stream().map(repository -> {
            IntStream counting = repositories.stream()
                    .filter(repo -> repo.getRepository().equals(repository.get("full_name").toString()))
                    .mapToInt(Repository::getCounting);

            return new RepositoryDTOResponse(repository.get("name").toString(), repository.get("full_name").toString(), counting.findFirst().orElse(0));
        }).collect(Collectors.toList());
    }

    private String getFullName(Map repo) {
        return repo.get("full_name").toString();
    }
}
