package com.incar.gitApi.query;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.jsonObj.Issue;
import com.incar.gitApi.service.GitResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by ct on 2016/2/24 0024.
 */
@EnableScheduling
@Component
@Configurable
public class ScheduledTask {

    @Autowired
    private GitResultService gitResultService;

    @Scheduled(cron = "0 0 9-23/1 * * ? ")
    public void scheduledQuery(){
        List<Issue> issues = gitResultService.queryGitApi();
        System.out.println("issue.size():"+issues.size());
        Set<GitResult> gitResults = gitResultService.issuesToGitResults(issues);
        gitResultService.saveGitResult(gitResults);
    }
}
