package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
}
