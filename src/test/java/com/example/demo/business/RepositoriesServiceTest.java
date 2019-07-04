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
        when(gitHubApiService.getRepositories("facebook"))
                .thenReturn(new ArrayList<>());
        when(repositoryMapper.mapper(Mockito.any()))
                .thenReturn(new ArrayList<>());

        List<RepositoryDTOResponse> response = repositoriesService.getRepositories("facebook");

        assertEquals(0, response.size());
    }

    @Test
    public void saveFavoriteShouldSaveNewRepository() {
        when(repositoryRepository.findByRepository("facebook/.github"))
                .thenReturn(null);

        Boolean response = repositoriesService.saveFavorite("facebook/.github");

        ArgumentCaptor<Repository> argumentCaptor = ArgumentCaptor.forClass(Repository.class);
        verify(repositoryRepository).save(argumentCaptor.capture());

        assertEquals(Integer.valueOf(1), argumentCaptor.getValue().getCounting());
        assertEquals("facebook/.github", argumentCaptor.getValue().getRepository());
        assertTrue(response);
    }

    @Test
    public void saveFavoriteShouldSaveIncrementCountingRepository() {
        when(repositoryRepository.findByRepository("facebook/.github"))
                .thenReturn(new Repository(1l, "facebook/.github", 1, LocalDateTime.now(), LocalDateTime.now()));

        Boolean response = repositoriesService.saveFavorite("facebook/.github");

        ArgumentCaptor<Repository> argumentCaptor = ArgumentCaptor.forClass(Repository.class);
        verify(repositoryRepository).save(argumentCaptor.capture());

        assertEquals(Integer.valueOf(2), argumentCaptor.getValue().getCounting());
        assertEquals("facebook/.github", argumentCaptor.getValue().getRepository());
        assertTrue(response);
    }

    @Test
    public void saveFavoriteRiseExceptionNotSave() {
        when(repositoryRepository.findByRepository("facebook/.github"))
                .thenReturn(new Repository(1l, "facebook/.github", 1, LocalDateTime.now(), LocalDateTime.now()));
        when(repositoryRepository.save(Mockito.any()))
                .thenThrow(new RuntimeException("error"));

        Boolean response = repositoriesService.saveFavorite("facebook/.github");

        assertFalse(response);
    }

    @Test
    public void unFavoriteShouldDecrementRepository() throws NotFound {
        when(repositoryRepository.findByRepository("facebook/.github"))
                .thenReturn(new Repository(1l, "facebook/.github", 10, LocalDateTime.now(), LocalDateTime.now()));

        boolean response = repositoriesService.unFavorite("facebook/.github");

        ArgumentCaptor<Repository> argumentCaptor = ArgumentCaptor.forClass(Repository.class);
        verify(repositoryRepository).save(argumentCaptor.capture());

        assertEquals(Integer.valueOf(9), argumentCaptor.getValue().getCounting());
        assertTrue(response);
    }

    @Test
    public void unFavoriteShouldDecrementWithNonZeroCountingRepository() throws NotFound {
        when(repositoryRepository.findByRepository("facebook/.github"))
                .thenReturn(new Repository(1l, "facebook/.github", 0, LocalDateTime.now(), LocalDateTime.now()));

        boolean response = repositoriesService.unFavorite("facebook/.github");

        ArgumentCaptor<Repository> argumentCaptor = ArgumentCaptor.forClass(Repository.class);
        verify(repositoryRepository).save(argumentCaptor.capture());

        assertEquals(Integer.valueOf(0), argumentCaptor.getValue().getCounting());
        assertTrue(response);
    }

    @Test
    public void unFavoriteShouldDecrementRiseNotFoundException()  {
        when(repositoryRepository.findByRepository("facebook/.github"))
                .thenReturn(null);

        try {
            repositoriesService.unFavorite("facebook/.github");
            fail();
        } catch (NotFound e) {
            assertEquals("Not found repository [facebook/.github]", e.getMessage());
        }
    }
}
