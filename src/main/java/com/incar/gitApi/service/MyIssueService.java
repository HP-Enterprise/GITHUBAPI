package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.entity.IssueShow;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.GistService;
import org.eclipse.egit.github.core.service.GitHubService;
import org.eclipse.egit.github.core.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
@Service
public class MyIssueService {
    @Autowired
    private GithubClientConfig githubClientConfig;

    /**
     * 添加一个issue
     *
     * @param user       用户名或组织名
     * @param repository 仓库名
     * @param issue      issue对象
     * @return
     * @throws IOException
     */
    public Issue addIssue(String user, String repository, Issue issue) throws IOException {
        IssueService issueService = new IssueService(githubClientConfig.getGitHubClient());
        Issue issue1 = issueService.createIssue(user, repository, issue);
        return issue1;
    }

    /**
     * 更新issue
     * @param user 用户名或组织名
     * @param repository
     * @param issueShow
     * @return
     * @throws IOException
     */
    public Issue editIssue(String user, String repository,Integer number, IssueShow issueShow) throws IOException{

        GitHubService gitHubService=new GistService(githubClientConfig.getGitHubClient());
        if(issueShow == null) {
            throw new IllegalArgumentException("issueShow cannot be null");
        } else {
            StringBuilder uri = new StringBuilder("/repos");
            uri.append('/').append(user).append('/').append(repository).append("/issues").append('/').append(number);
            return (Issue)gitHubService.getClient().post(String.valueOf(uri),issueShow,Issue.class);
        }

    }
}
