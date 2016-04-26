package com.incar.gitApi;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Component
@ConfigurationProperties(prefix = "github")
public class GithubClientConfig {

    private String username;

    private String password;

    private List<String> repos = new ArrayList<>();

    private static final GitHubClient gitHubClient = new GitHubClient();

    public void setUsername(String username){this.username = username;}

    public String getUsername() {
        return username;
    }

    public void setPassword(String password){ this.password = password;}

    public String getPassword() {
        return password;
    }

    public List<String> getRepos() {
        return repos;
    }

    public void setRepos(List<String> repos) {
        this.repos = repos;
    }

    public GitHubClient getGitHubClient(){
        return gitHubClient.setCredentials(username,password);
    }
}
