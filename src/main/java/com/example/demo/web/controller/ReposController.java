package com.example.demo.web.controller;

import com.example.demo.business.RepositoriesService;
import com.example.demo.dto.SaveFavoriteDTORequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/repos")
public class ReposController {


    @Autowired
    RepositoriesService repositoriesService;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List getRepositories(@PathVariable String username)  {

        return repositoriesService.getRepositories(username);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void saveFavorite(@RequestBody @Valid SaveFavoriteDTORequest saveFavoriteDTORequest) {
        repositoriesService.saveFavorite(saveFavoriteDTORequest.getRepositoryName());
    }
}
