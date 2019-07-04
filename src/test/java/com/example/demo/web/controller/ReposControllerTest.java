package com.example.demo.web.controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.github.tomakehurst.wiremock.client.WireMock;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReposControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9999);


    @Test
    public void test01getRepositoriesShouldDisplayOneRepo() throws Exception {

        WireMock.stubFor(WireMock.get("/users/facebook/repos")
                .willReturn(
                        WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(buildGitHubResponse())
                )
        );

        mockMvc.perform(get("/api/v1/repos/facebook"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.content().json("[{\"name\":\".github\",\"fullName\":\"facebook/.github\",\"counting\":0}]"));
    }

    @Test
    public void test02saveFavoriteShouldSave() throws Exception {
        mockMvc.perform(post("/api/v1/repos")
                .content("{\"repositoryName\":\"facebook/.github\"}")
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void test03getRepositoriesShouldDisplayOneRepoWithCounting() throws Exception {

        WireMock.stubFor(WireMock.get("/users/facebook/repos")
                .willReturn(
                        WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(buildGitHubResponse())
                )
        );

        mockMvc.perform(get("/api/v1/repos/facebook"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json("[{\"name\":\".github\",\"fullName\":\"facebook/.github\",\"counting\":1}]"));
    }

    @Test
    public void test04getRepositoriesShouldZeroRepos() throws Exception {

        WireMock.stubFor(WireMock.get("/users/facebook/repos")
                .willReturn(
                        WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("[]")
                )
        );

        mockMvc.perform(get("/api/v1/repos/facebook"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    private String buildGitHubResponse() {
        return "[{\"id\":172581071,\"node_id\":\"MDEwOlJlcG9zaXRvcnkxNzI1ODEwNzE=\",\"name\":\".github\",\"full_name\":\"facebook/.github\",\"private\":false,\"owner\":{\"login\":\"facebook\",\"id\":69631,\"node_id\":\"MDEyOk9yZ2FuaXphdGlvbjY5NjMx\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/69631?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/facebook\",\"html_url\":\"https://github.com/facebook\",\"followers_url\":\"https://api.github.com/users/facebook/followers\",\"following_url\":\"https://api.github.com/users/facebook/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/facebook/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/facebook/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/facebook/subscriptions\",\"organizations_url\":\"https://api.github.com/users/facebook/orgs\",\"repos_url\":\"https://api.github.com/users/facebook/repos\",\"events_url\":\"https://api.github.com/users/facebook/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/facebook/received_events\",\"type\":\"Organization\",\"site_admin\":false},\"html_url\":\"https://github.com/facebook/.github\",\"description\":\"Default Community health files for Facebook projects\",\"fork\":false,\"url\":\"https://api.github.com/repos/facebook/.github\",\"forks_url\":\"https://api.github.com/repos/facebook/.github/forks\",\"keys_url\":\"https://api.github.com/repos/facebook/.github/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/facebook/.github/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/facebook/.github/teams\",\"hooks_url\":\"https://api.github.com/repos/facebook/.github/hooks\",\"issue_events_url\":\"https://api.github.com/repos/facebook/.github/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/facebook/.github/events\",\"assignees_url\":\"https://api.github.com/repos/facebook/.github/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/facebook/.github/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/facebook/.github/tags\",\"blobs_url\":\"https://api.github.com/repos/facebook/.github/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/facebook/.github/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/facebook/.github/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/facebook/.github/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/facebook/.github/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/facebook/.github/languages\",\"stargazers_url\":\"https://api.github.com/repos/facebook/.github/stargazers\",\"contributors_url\":\"https://api.github.com/repos/facebook/.github/contributors\",\"subscribers_url\":\"https://api.github.com/repos/facebook/.github/subscribers\",\"subscription_url\":\"https://api.github.com/repos/facebook/.github/subscription\",\"commits_url\":\"https://api.github.com/repos/facebook/.github/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/facebook/.github/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/facebook/.github/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/facebook/.github/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/facebook/.github/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/facebook/.github/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/facebook/.github/merges\",\"archive_url\":\"https://api.github.com/repos/facebook/.github/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/facebook/.github/downloads\",\"issues_url\":\"https://api.github.com/repos/facebook/.github/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/facebook/.github/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/facebook/.github/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/facebook/.github/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/facebook/.github/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/facebook/.github/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/facebook/.github/deployments\",\"created_at\":\"2019-02-25T20:39:32Z\",\"updated_at\":\"2019-03-19T16:40:58Z\",\"pushed_at\":\"2019-03-19T16:40:57Z\",\"git_url\":\"git://github.com/facebook/.github.git\",\"ssh_url\":\"git@github.com:facebook/.github.git\",\"clone_url\":\"https://github.com/facebook/.github.git\",\"svn_url\":\"https://github.com/facebook/.github\",\"homepage\":null,\"size\":1,\"stargazers_count\":1,\"watchers_count\":1,\"language\":null,\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":false,\"has_pages\":false,\"forks_count\":6,\"mirror_url\":null,\"archived\":false,\"disabled\":false,\"open_issues_count\":0,\"license\":null,\"forks\":6,\"open_issues\":0,\"watchers\":1,\"default_branch\":\"master\"}]";
    }
}
