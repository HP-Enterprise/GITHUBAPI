package com.incar.gitApi;



import com.incar.gitApi.entity.UserAccount;
import com.incar.gitApi.service.TokenService;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Component
@ConfigurationProperties(prefix = "github")
public class GithubClientConfig {
    @Autowired
    private TokenService tokenService;
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
    public  GitHubClient getClient(String token){
        UserAccount   userAccount= tokenService.loadToken(token);
        return gitHubClient.setCredentials(userAccount.getUsername(),userAccount.getPassword());
    }
    public  String getClientName(String token){
        UserAccount   userAccount= tokenService.loadToken(token);
        return userAccount.getUsername();
    }
}
