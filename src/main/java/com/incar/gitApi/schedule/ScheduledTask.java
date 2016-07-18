package com.incar.gitApi.schedule;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.service.GitResultService;
import com.incar.gitApi.service.WorkDetailService;
import com.incar.gitApi.service.WorkService;
import org.eclipse.egit.github.core.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private WorkDetailService workDetailService;

    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);



    @Scheduled(cron = "0 14 10 * * ?")

    public void scheduledQuery(){
        logger.info(">>>>>>>>>>> saving gitResult >>>>>>>>>>>>");
        gitResultService.saveGitResult();
    }

    @Scheduled(cron = "0 16 10 * * ?")

    public void gitRetDetail(){
        logger.info(">>>>>>>>>>> deleting workDetailInfo >>>>>>>>>>>>");
        workDetailService.deleteWorkDetailInfo();
        logger.info(">>>>>>>>>>> saving workDetailInfo >>>>>>>>>>>>");
        workDetailService.saveWorkDetailInfo();
    }
//    @Scheduled(cron = "0 */1 * * * ?")



    @Scheduled(cron = "0 16 10 * * ?")

    public void gitRetAlalyse(){
        logger.info(">>>>>>>>>>> deleting workInfo >>>>>>>>>>>>");
        workService.deleteWorkInfo();
        logger.info(">>>>>>>>>>> saving workInfo >>>>>>>>>>>>");
        workService.saveWorkInfo();
    }

}
