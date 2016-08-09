package com.incar.gitApi.schedule;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.WorkRepository;
import com.incar.gitApi.service.GitResultService;
import com.incar.gitApi.service.WorkDetailService;
import com.incar.gitApi.service.WorkService;
import com.incar.gitApi.util.DateUtil;
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
    @Autowired
    private WorkRepository workRepository;

    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);



    @Scheduled(cron = "0 54 11 * * ?")
   // @Scheduled(cron = "0 40 18 * * ?")
    public void scheduledQuery(){
        logger.info(">>>>>>>>>>> saving gitResult >>>>>>>>>>>>");
        gitResultService.saveGitResult();
    }

    @Scheduled(cron = "0 0 19 * * ?")

    public void gitRetDetail(){
        logger.info(">>>>>>>>>>> deleting workDetailInfo >>>>>>>>>>>>");
        workDetailService.deleteWorkDetailInfo();
        logger.info(">>>>>>>>>>> saving workDetailInfo >>>>>>>>>>>>");
        workDetailService.saveWorkDetailInfo();
    }
     @Scheduled(cron = "0 0 19 * * ?")
    public void gitRetAlalyse(){
        logger.info(">>>>>>>>>>> deleting workInfo >>>>>>>>>>>>");
        for(int i=1;i<= DateUtil.getWeekInYear();i++) {
            workRepository.deleteByWeek(i);
        }
        logger.info(">>>>>>>>>>> saving workInfo >>>>>>>>>>>>");
        for(int i=1;i<= DateUtil.getWeekInYear();i++){
            workService.saveWorkInfo(i);
        }
    }

}
