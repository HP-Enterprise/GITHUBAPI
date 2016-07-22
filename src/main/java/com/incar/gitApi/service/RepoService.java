package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Administrator on 2016/7/19.
 */
@Service
public class RepoService {
    private Repository repository1;
    @Autowired
    private GithubClientConfig githubClientConfig;

    /**
     * 添加仓库
     * @param repository
     * @return
     */
    public Repository  addRepository(Repository repository){
        RepositoryService repositoryService=new RepositoryService(githubClientConfig.getGitHubClient());
        try {
            repository1 =  repositoryService.createRepository(repository);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repository1;
    }
    public Issue  addIssue(String repository,Issue issue )throws IOException{
        IssueService issueService= new IssueService(githubClientConfig.getGitHubClient());
       Issue issue1=   issueService.createIssue(githubClientConfig.getUsername(), repository, issue);
       return issue1;
    }
}
