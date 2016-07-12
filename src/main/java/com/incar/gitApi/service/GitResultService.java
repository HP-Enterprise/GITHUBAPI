package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.schedule.ScheduledTask;
import com.incar.gitApi.util.DateUtil;
import com.incar.gitApi.util.GitRetUtil;
import com.incar.gitApi.GithubClientConfig;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

/**
 * Created by ct on 2016/2/19 0019.
 */
@Service
public class GitResultService {


    private GithubClientConfig githubClientConfig;

    private GitResultRepository gitResultRepository;

    private Logger logger = LoggerFactory.getLogger(GitResultService.class);

    @Autowired
    public void setGithubClientConfig(GithubClientConfig githubClientConfig){
        this.githubClientConfig = githubClientConfig;
    }

    @Autowired
    public void setGitResultRepository(GitResultRepository gitResultRepository){
        this.gitResultRepository = gitResultRepository;
    }

    @Transactional
    public void saveGitResult(){
        List<Issue> issues = getAllIssues();
        List<GitResult> gitResults = getGitResult(issues);
        gitResultRepository.save(gitResults);
    }

    public List<GitResult> findAll(){
        return gitResultRepository.findAll();
    }


    public List<Issue> getIssues(String user,String repository){
        Map<String,String> params = new HashMap<String,String>();
        params.put("state","all");
        IssueService issueService = new IssueService(githubClientConfig.getGitHubClient());
        List<Issue> issueList = new ArrayList<>();
        try {
            issueList = issueService.getIssues(user, repository, params);
        } catch (IOException e) {
            logger.error("connecting failed",e);
        }
        return issueList;
    }

    public List<Issue> getAllIssues(){
        List<Issue> issues = new ArrayList<>();
        List<String> repos = githubClientConfig.getRepos();
        for (String str : repos){
            String[] keyValue = str.split("/");
            issues.addAll(this.getIssues(keyValue[0], keyValue[1]));
        }
        return issues;
    }


    public List<GitResult> getGitResult(List<Issue> issues){
        return GitRetUtil.issuesToGitresults(issues);
    }

}
