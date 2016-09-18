package com.incar.gitApi.service;

import com.google.gson.reflect.TypeToken;
import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.entity.Status;
import org.eclipse.egit.github.core.CommitStats;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.GistService;
import org.eclipse.egit.github.core.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
@Service
public class MyStatisticsService {
    @Autowired
    private GithubClientConfig githubClientConfig;

    public Status getPunchCards(String org,String repository)throws IOException{
        GitHubService gitHubService=new GistService(githubClientConfig.getGitHubClient());
        StringBuilder uri = new StringBuilder("/repos");
        uri.append('/').append(org);
        uri.append('/').append(repository);
        uri.append("/stats");
        uri.append("/punch_card");
        PagedRequest pagedRequest=new PagedRequest(1,1000);
     pagedRequest.setUri(uri);
        pagedRequest.setArrayType(Status.class);
        return  (Status)gitHubService.getClient().get(pagedRequest).getBody();
    }

}
