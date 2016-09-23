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
    public Issue addIssue(String user, String repository, Issue issue,String token) throws IOException {
        IssueService issueService = new IssueService(githubClientConfig.getClient(token));
        Issue issue1 = issueService.createIssue(user, repository, issue);
        return issue1;
    }

    /**
     * 更新issue
     * @param user 用户名或组织名
     * @param repository
     * @param issue
     * @return
     * @throws IOException
     */
    public Issue editIssue(String user, String repository, Issue issue,String token) throws IOException{
        IssueService issueService = new IssueService(githubClientConfig.getClient(token));
        Issue issue1=   issueService.editIssue(user, repository, issue);
        return issue1;
    }
}
