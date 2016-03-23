package com.incar.gitApi.util;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.service.GitResultService;
import com.incar.gitApi.service.WorkService;
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

    @Autowired
    private WorkService workService;

    @Scheduled(cron = "0 */1 * * * ? ")
    public void scheduledQuery(){
        List<Issue> issues = gitResultService.getAllIssues();
        System.out.println("issue.size():" + issues.size());
        List<GitResult> gitResults = gitResultService.getGitResult(issues);
        System.out.println("gitresult.size():"+gitResults.size());
        gitResultService.saveGitResult(gitResults);
    }

    @Scheduled(cron = "0 43 17 ? * WED")   //    @Scheduled(cron = "0 */2 * * * ?")
    public void gitRetAlalyse(){
        workService.saveWorkInfo();
    }
}
