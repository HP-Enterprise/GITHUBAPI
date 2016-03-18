package com.incar.gitApi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Component
@ConfigurationProperties(prefix="repositories")
public class SimpleConfig {

    private List<String> repositories;

    public void setRepositories(List<String> repositories){this.repositories = repositories;}

    public List<String> getRepositories(){
        return repositories;
    }
}
