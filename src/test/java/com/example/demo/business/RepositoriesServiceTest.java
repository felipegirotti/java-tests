package com.example.demo.business;

import com.example.demo.dto.RepositoryDTOResponse;
import com.example.demo.dto.exception.NotFound;
import com.example.demo.persistence.entity.Repository;
import com.example.demo.persistence.repository.RepositoryRepository;
import com.example.demo.service.GitHubApiService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class RepositoriesServiceTest {

    @Mock
    GitHubApiService gitHubApiService;

    @Mock
    RepositoryRepository repositoryRepository;

    @Mock
    RepositoryMapper repositoryMapper;

    RepositoriesService repositoriesService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        repositoriesService = new RepositoriesService(gitHubApiService, repositoryRepository, repositoryMapper);
    }

    @Test
    public void getRepositoryShouldReturnListOfRepositories() {

        List<RepositoryDTOResponse> responseMapper = Arrays.asList(
                new RepositoryDTOResponse(".github", "facebook/.github", 0),
                new RepositoryDTOResponse("android-jsc", "facebook/android-jsc", 2)
        );

        when(gitHubApiService.getRepositories("facebook"))
            .thenReturn(new ArrayList<>());

        when(repositoryMapper.mapper(Mockito.any()))
                .thenReturn(responseMapper);

        List<RepositoryDTOResponse> response = repositoriesService.getRepositories("facebook");

        assertEquals(2, response.size());
        assertEquals("facebook/.github", response.get(0).getFullName());
        assertEquals(Integer.valueOf(2), response.get(1).getCounting());
    }

    @Test
    public void getRepositoriesShoudResultEmptyList() {
        // mockar gitHubApiService para array vazio
        // mockar repositoryMapper para array vazio

    }

    @Test
    public void saveFavoriteShouldSaveNewRepository() {
        // mockar repository para retornar null
        // ArgumentCaptor<Repository> argumentCaptor = ArgumentCaptor.forClass(Repository.class);
        // verify(repositoryRepository).save(argumentCaptor.capture());

    }

    @Test
    public void saveFavoriteShouldSaveIncrementCountingRepository() {
        // mockar repository para retornar uma entity

    }

    @Test
    public void saveFavoriteRiseExceptionNotSave() {
        // mockar repository findByRepository
        // mockar repository thenThrow(new RuntimeException())
    }

    // Criar nova feature unfavorite
    // TDD
    // Testes:
    // unFavoriteShouldDecrementRepository
    // unFavoriteShouldDecrementWithNonZeroCountingRepository
    // unFavoriteShouldDecrementRiseNotFoundException
}
