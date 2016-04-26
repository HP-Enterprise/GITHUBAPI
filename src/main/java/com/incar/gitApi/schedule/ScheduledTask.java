package com.incar.gitApi.schedule;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.service.GitResultService;
import com.incar.gitApi.service.WorkService;
import org.eclipse.egit.github.core.Issue;
import org.slf4j.Logger;
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

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(cron = "0 */1 * * * ? ")
    public void scheduledQuery(){
        List<Issue> issues = gitResultService.getAllIssues();
        List<GitResult> gitResults = gitResultService.getGitResult(issues);
        logger.info("<-----------------saving gitResult--------------------->");
        gitResultService.saveGitResult(gitResults);
    }

    //@Scheduled(cron = "0 0 0 ? * SAT")   //
    @Scheduled(cron = "0 */2 * * * ?")
    public void gitRetAlalyse(){
        workService.deleteWorkInfo();
        logger.info("<-----------------saving workInfo-------------------->");
        workService.saveWorkInfo();
    }
}
