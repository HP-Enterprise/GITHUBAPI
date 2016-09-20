package com.incar.gitApi.schedule;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.TaskRepository;
import com.incar.gitApi.repository.WorkRepository;
import com.incar.gitApi.service.*;
import com.incar.gitApi.util.DateUtil;
import org.eclipse.egit.github.core.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskStatService taskStatService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    public WorkStatService workStatService;
    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);



    @Scheduled(cron = "${git.gitResult.cron}")
   // @Scheduled(cron = "0 40 18 * * ?")
    public void scheduledQuery(){
        logger.info(">>>>>>>>>>> saving gitResult >>>>>>>>>>>>");
        gitResultService.saveGitResult();
    }

    @Scheduled(cron = "${git.taskStatus.cron}")
    // @Scheduled(cron = "0 40 18 * * ?")
    public void saveTaskStatusInfo()throws IOException{
        logger.info(">>>>>>>>>>> delete taskStatusInfo >>>>>>>>>>>>");
        taskRepository.deleteByWeek(DateUtil.getYear(),DateUtil.getWeekInYear());
        logger.info(">>>>>>>>>>> save  taskStatusInfo >>>>>>>>>>>>");
        taskStatService.saveTaskInfo();
    }
    @Scheduled(cron = "${git.projectInfo.cron}")
    // @Scheduled(cron = "0 40 18 * * ?")
    public void saveProjectInfo()throws IOException{
        logger.info(">>>>>>>>>>> delete  ProjectInfo >>>>>>>>>>>>");
        projectService.deleteProject();
        logger.info(">>>>>>>>>>> save  ProjectInfo >>>>>>>>>>>>");
        projectService.saveProject();
    }
    @Scheduled(cron = "${git.gitDetail.cron}")
    public void gitRetDetail(){
        logger.info(">>>>>>>>>>> deleting workDetailInfo >>>>>>>>>>>>");
        workDetailService.deleteWorkDetailInfo();
        logger.info(">>>>>>>>>>> saving workDetailInfo >>>>>>>>>>>>");
        workDetailService.saveWorkDetailInfo();
    }
     @Scheduled(cron = "${git.gitWeek.cron}")
    public void gitWeek(){
//        logger.info(">>>>>>>>>>> deleting workInfo >>>>>>>>>>>>");
//        for(int i=1;i<= DateUtil.getWeekInYear();i++) {
//            workRepository.deleteByWeek(i);
//        }
//        logger.info(">>>>>>>>>>> saving workInfo >>>>>>>>>>>>");
//        for(int i=1;i<= DateUtil.getWeekInYear();i++){
//            workService.saveWorkInfo(i);
//        }
         logger.info(">>>>>>>>>>> deleting workInfo >>>>>>>>>>>>");
         workService.deleteWorkInfo();
         logger.info(">>>>>>>>>>> saving workInfo >>>>>>>>>>>>");
         workService.saveWorkInfo(DateUtil.getWeekInYear());
    }
    @Scheduled(cron ="${git.gitMonth.cron}")
    public void gitMonth(){

//        for(int i=1;i<= 9;i++){
//            workStatService.saveStat(DateUtil.getYear(),i);
//        }
        if(DateUtil.getMonth()==12){
            logger.info(">>>>>>>>>>> saving workOfMonth >>>>>>>>>>>>");
            workStatService.saveStat(DateUtil.getYear()-1, DateUtil.getMonth());
        }else{
            logger.info(">>>>>>>>>>> saving workOfMonth >>>>>>>>>>>>");
            workStatService.saveStat(DateUtil.getYear(), DateUtil.getMonth());
        }

    }

    @Scheduled(cron ="${git.gitQuarter.cron}")
    public void gitQuarter(){
        if(DateUtil.getMonth()==12) {
            logger.info(">>>>>>>>>>> delete  workOfQuarter >>>>>>>>>>>>");
            workRepository.deleteByQuarter(DateUtil.getQuarter(), DateUtil.getYear()-1);

            logger.info(">>>>>>>>>>> saving workOfQuarter >>>>>>>>>>>>");
            workStatService.getQuarterWorkStat(DateUtil.getYear()-1, DateUtil.getQuarter());
        }else{
            logger.info(">>>>>>>>>>> delete  workOfQuarter >>>>>>>>>>>>");
            workRepository.deleteByQuarter(DateUtil.getQuarter(), DateUtil.getYear());

            logger.info(">>>>>>>>>>> saving workOfQuarter >>>>>>>>>>>>");
            workStatService.getQuarterWorkStat(DateUtil.getYear(), DateUtil.getQuarter());
        }
    }
    @Scheduled(cron ="${git.gitYear.cron}")
    public void gitYear(){
        if(DateUtil.getMonth()==12) {
            logger.info(">>>>>>>>>>> delete  workOfYear >>>>>>>>>>>>");
            workRepository.deleteByYear(DateUtil.getYear()-1);

            logger.info(">>>>>>>>>>> saving workOfYear >>>>>>>>>>>>");
            workStatService.getQuarterWorkStat(DateUtil.getYear()-1, 52);
        }else{
            logger.info(">>>>>>>>>>> delete  workOfYear >>>>>>>>>>>>");
            workRepository.deleteByYear(DateUtil.getYear());

            logger.info(">>>>>>>>>>> saving workOfYear >>>>>>>>>>>>");
            workStatService.getQuarterWorkStat(DateUtil.getYear(), DateUtil.getWeekInYear());
        }
    }
}
