package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.Task;
import com.incar.gitApi.period.Period;
import com.incar.gitApi.period.PeriodFactory;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.repository.TaskRepository;
import com.incar.gitApi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2016/8/30.
 */
@Service
public class TaskStatService {
    @Autowired
    private WorkService workService;
    @Autowired
    private GitResultRepository gitResultRepository;
    @Autowired
    private TaskRepository taskRepository;
    private static List<Period> periods = null;
    private static int previousVal = 0;
    public static List<Period> getPeriods(int year, int weekOfYear) {
        if (weekOfYear != previousVal || periods == null) {
            periods = Arrays.asList(PeriodFactory.generatePeriods(year, weekOfYear));
        }
        previousVal = weekOfYear;
        return periods;
    }

    /**
     * 保存本年本周统计任务
     */
    public void saveTaskInfo() {
        List<Task> tasks = new ArrayList<>();
        Properties properties = workService.getRealnameProperties();
        for (String org : gitResultRepository.findAllOrg()) {
            for (String project : gitResultRepository.findAllProject()) {
                for (String assignee : gitResultRepository.findAllAssignee()) {
                    if (assignee != null && project != null) {
                        Task task = getTaskWorkInfo(project,org, assignee);
                        Object obj = properties.get(assignee);
                        if (obj != null) {
                            task.setRealname((String) obj);
                        }
                        tasks.add(task);
                    }
                }
            }
        }
        taskRepository.save(tasks);
    }



    /**
     * 保存某年某周统计任务
     */
    public void saveTaskInfo(int weekYear, int weekOfYear) {
        List<Task> tasks = new ArrayList<>();
        Properties properties = workService.getRealnameProperties();
        for (String org : gitResultRepository.findAllOrg()) {
            for (String project : gitResultRepository.findAllProject()) {
                for (String assignee : gitResultRepository.findAllAssignee()) {
                    if (assignee != null && project != null) {
                        Task task = getTaskWorkInfo(project,org, assignee, weekYear, weekOfYear);
                        Object obj = properties.get(assignee);
                        if (obj != null) {
                            task.setRealname((String) obj);
                        }
                        tasks.add(task);
                    }
                }
            }
        }
        taskRepository.save(tasks);
        tasks.clear();
    }



    /**
     *获取本年本周信息
     * @param project
     * @param assignee
     * @return
     */
    public Task getTaskWorkInfo(String project,String org,String assignee) {
        return this.getTaskWorkInfo(project,org, assignee, DateUtil.getYear(), DateUtil.getWeekInYear());
    }

    /**
     *
     * @param project
     * @param username
     * @param weekYear
     * @param weekOfYear
     * @return
     */
    public Task getTaskWorkInfo(String project,String org,String username, int weekYear, int weekOfYear) {
        initPeriods(this.getPeriods(weekYear, weekOfYear));//
        Date start = DateUtil.getWeekStart(weekYear, weekOfYear);
        Date end = DateUtil.getWeekEnd(weekYear, weekOfYear);
        List<GitResult> openGitRets = gitResultRepository.findOpenTaskGit(project,org, username, "open", end);
        List<GitResult> closedGitRets = gitResultRepository.findClosedTaskGit(project,org,username,"closed",start,end);
        List<GitResult> gitResultList=gitResultRepository.findAllTaskGit(project,org,username);
        Date time = null;
        if (weekOfYear != DateUtil.getWeekInYear()) {
            time = DateUtil.getWeekEnd(weekYear, weekOfYear);
        } else {
            time = new Date();
        }
        List<Period> periods1 = workService.getPeriodsByEnd(time, weekOfYear, periods);
        Task task = new Task();
        task.setFinishedWork(workService.getTotalFinishedWork(closedGitRets));
        task.setUnfinishedWork(workService.getTotalUnfinishedWork(openGitRets));
        task.setWorkHours(workService.getHoursInWork(workService.getGitRetHasLabelHOrD(gitResultList), weekOfYear, periods1));
        task.setWeekInYear(weekOfYear);
        task.setUsername(username);
        task.setProject(project);
        task.setOrg(org);
        task.setYear(weekYear);
        periods1.clear();
        openGitRets.clear();
        closedGitRets.clear();
        gitResultList.clear();
        return task;
    }
    /**
     * 重新赋值
     *
     * @param periods
     */
    private static void initPeriods(List<Period> periods) {
        for (Period period : periods) {
            period.setIsInWork(false);
        }
    }
    public Page<Task> findPageOfTask(String project ,String org,String username,String realname,Integer weekInYear,Integer year,Integer currentPage,Integer pageSize){
        currentPage=(currentPage==null||currentPage<=0)?1:currentPage;
        pageSize=(pageSize==null||pageSize<=0)?10:pageSize;
        if(username!=null){username="%"+username+"%";}//设置模糊查询
        if(realname!=null){realname="%"+realname+"%";}
        Pageable pageable = new PageRequest(currentPage-1,pageSize,new Sort(Sort.Direction.DESC,"weekInYear"));
        Page<Task> taskPage= taskRepository.findTaskPage(project,org, username,realname,  weekInYear, year, pageable);
        return new PageImpl<Task>(taskPage.getContent(),pageable,taskPage.getTotalElements());
    }
}
