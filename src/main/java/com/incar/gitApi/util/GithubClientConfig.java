package com.incar.gitApi.util;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Component
public class GithubClientConfig {

    private String username;

    private String password;

    private final GitHubClient gitHubClient = new GitHubClient();

    @Value("${github.username}")
    public void setUsername(String username){this.username = username;}

    public String getUsername() {
        return username;
    }

    @Value("${github.password}")
    public void setPassword(String password){ this.password = password;}

    public String getPassword() {
        return password;
    }

    public GitHubClient getGitHubClient(){
        return gitHubClient.setCredentials(username,password);
    }
}
