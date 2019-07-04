package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RepositoryRepository extends PagingAndSortingRepository<Repository, Long> {

    public Repository findByRepository(String repository);

    public List<Repository> findByRepositoryIn(List<String> repositories);
}
