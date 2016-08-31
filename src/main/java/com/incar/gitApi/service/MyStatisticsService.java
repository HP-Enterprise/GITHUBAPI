package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.entity.Status;
import org.eclipse.egit.github.core.CommitStats;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.User;
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

    public List<Status> getPunchCards(String org,String repository)throws IOException{
        StatisticsService statisticsService=new StatisticsService(githubClientConfig.getGitHubClient());

      List<Status>  list= statisticsService.getPunchCard(org,repository);
        return list;
    }

}
