package com.incar.gitApi.util;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.service.GitResultService;
import org.eclipse.egit.github.core.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ct on 2016/2/24 0024.
 */
@EnableScheduling
@Component
@Configurable
public class ScheduledTask {

    @Autowired
    private GitResultService gitResultService;

    @Scheduled(cron = "0 0 */1 * * ? ")
    public void scheduledQuery(){
        List<Issue> issues = gitResultService.getAllIssues();
        System.out.println("issue.size():" + issues.size());
        List<GitResult> gitResults = gitResultService.getGitResult(issues);
        System.out.println("gitresult.size():"+gitResults);
        gitResultService.saveGitResult(gitResults);
    }
}
