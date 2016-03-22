package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.Work;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.util.GithubClientConfig;
import com.incar.gitApi.util.Period;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Service
public class WorkService {

    private String username;

    private String password;

    private GitResultRepository gitResultRepository;

    private GithubClientConfig githubClientConfig;

    @Autowired
    public void setGitResultRepository(GitResultRepository gitResultRepository){this.gitResultRepository = gitResultRepository;}

    @Autowired
    public void setGithubClientConfig(GithubClientConfig githubClientConfig){
        this.githubClientConfig = githubClientConfig;
    }


    //获取所有人的工作信息
    public List<Work> getWorkInfo(int weekOfYear){
        Date date1 = null;
        Date date2 = null;
        //根据周数获取本周的开始时间和结束时间
        for (String assignee : this.getAllAssignee() ){
            Work work = getWorkInfo(assignee,date1,date2);
        }
        return Collections.emptyList();
    }


    public List<String> getAllAssignee(){
        return gitResultRepository.findAllAssignee();
    }

    //获取某个员工的工作信息
    public Work getWorkInfo(String assignee,Date date1,Date date2){
        Work work = new Work();
        //get finished work
        int finishedWork = this.getTotalFinishedWork(assignee, date1, date2);
        work.setFinishedWork(finishedWork);
        //get unfinished work
        int unfinishedWork = this.getUnfinishedWorkOfAssignee(assignee);
        work.setUnfinishedWork(unfinishedWork);
        //get workload

        return work;
    }


    public int totalFinishedWork(List<GitResult> gitResults){
        int totalFinished = 0;
        for(GitResult gitResult : gitResults){
            totalFinished += this.oneIssueWork(gitResult);
        }
        return totalFinished;
    }


    public int oneIssueWork(GitResult gitResult){
        String labels = gitResult.getLabels();
        Pattern pattern = Pattern.compile("([DH]\\d)");
        int n = 0,sum = 0 ;
        if(labels != null) {
            Matcher matcher = pattern.matcher(labels);
            if (matcher.find()) {
                String workAmount = matcher.group(1);
                n = Integer.parseInt(String.valueOf(workAmount.charAt(1)));
                sum = workAmount.charAt(0) == 'H' ? n : 8 * n;
            }
        }
        return sum;
    }


    public List<GitResult> getClosedGitRet(String assignee,Date date1 ,Date date2){
        return gitResultRepository.findClosedGitRet(assignee, "closed", date1, date2);
    }

    public List<GitResult> getOpenGitRet(String assignee){
        return gitResultRepository.findOpenGitRet(assignee, "open");
    }


    public int getTotalFinishedWork(String assignee,Date date1 ,Date date2){
        return this.totalFinishedWork(getClosedGitRet(assignee, date1, date2));
    }


    public int getUnfinishedWorkOfAssignee(String assignee){
        return getTotalUnfinishedWork(getOpenGitRet(assignee));
    }


    //获得规定应在本周之前完成的GitRet（即milestone为本周之前的GitRet）
    public List<GitResult> selectGitRetDueOnThisWeek(List<GitResult> gitResults){
        List<GitResult> gitRets = new ArrayList<>();
        for (GitResult gitResult : gitResults){
            if(isBeforeThisWeek(gitResult.getDueOn())){
                gitRets.add(gitResult);
            }
        }
        return gitRets;
    }


    public int getUnfinishedWorkOfOneIssue(GitResult gitResult){
        int num = 0;
        if(isBeforeThisWeek(gitResult.getDueOn())) {
            return num = oneIssueWork(gitResult);
        }
        return num;
    }


    public int getTotalUnfinishedWork(List<GitResult> gitResults){
        int totalHours = 0;
        for(GitResult gitResult : gitResults){
            totalHours += this.getUnfinishedWorkOfOneIssue(gitResult);
        }
        return totalHours;
    }

    //判断某个时间是否在本周之前
    public boolean isBeforeThisWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        Date date2 = calendar.getTime();
        if(date != null && date.compareTo(date2)<=0){
            return true;
        }
        return false;
    }

    //获取某个GitRet的milestone
    public Milestone getMileStone(GitResult gitResult){
        Milestone milestone = null;
        try {
            MilestoneService milestoneService = new MilestoneService(githubClientConfig.getGitHubClient());
            if(gitResult.getMilestone() != null)
              milestone = milestoneService.getMilestone(gitResult.getUser(),gitResult.getProject(),gitResult.getMilestone());
        }catch (IOException e){
            e.printStackTrace();
        }
        return milestone;
    }





//    public List<Period> getWorkPeriodOfOneIssue(GitResult gitResult,List<Period> periods){
//        //这里的gitResult只针对于本周里程碑下的issue
//        Date createdAt = gitResult.getCreatedAt();
//        Date closedAt = gitResult.getClosedAt();
//        if(closedAt != null){//没有关闭的issue
//
//        }
//        for(Period period:periods){
//            if(period.getIsInWork()){
//                continue;
//
//            }
//            while(){
//                period.setIsInWork(true);
//            }
//        }
//        return periods;
//    }
//
//    public List<Period> getWorkPeriodOfOpenIssue(Date createdAt,List<Period> periods,Calendar calendar){
//        calendar.setTime(createdAt);
//        for (Period period : periods){
//            if(isInPeriod(createdAt, period)){
//               calendar.add();
//            }
//        }
//        return null;
//    }


    //判断某个时间点是否在某个片段内
    public boolean isInPeriod(Date date , Period period){
        Calendar calendar = Calendar.getInstance();
//        System.out.println("第几周：" + calendar.get(Calendar.WEEK_OF_YEAR));
        calendar.setWeekDate(period.getYear(), period.getWeekOfYear(), period.getDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, period.getHourOfDay());
        calendar.set(Calendar.MINUTE,period.getMinute());
        calendar.set(Calendar.SECOND,period.getSeconds());
        Date date1 = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, period.getHourOfDay() + 1);
        Date date2 = calendar.getTime();
        if(date.compareTo(date1)>=0 && date.compareTo(date2)<0){
            return true;
        }
        return false;
    }


    //获取period对应的date
    public Date getTimeOfPeriod(Period period){
        Calendar calendar = Calendar.getInstance();
        calendar.setWeekDate(period.getYear(), period.getWeekOfYear(), period.getDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, period.getHourOfDay());
        calendar.set(Calendar.MINUTE, period.getMinute());
        calendar.set(Calendar.SECOND,period.getSeconds());
        return calendar.getTime();
    }


    //计算createdAt所在的period
    public Period getPeriodOfCreated(Date createdAt,List<Period> periods){

        if(isBeforeFirstPeriod(createdAt,periods.get(0))){
            return periods.get(0);
        }
        if(isNotInPeriod(createdAt)){
            return getFirstPeriodAfterCreated(createdAt,periods);
        }
        for(Period period : periods){
            if(isInPeriod(createdAt,period)){
                return period;
            }
        }
        return null;
    }


    //计算closedAt所在的period
    public Period getPreiodOfClosed(Date closedAt ,List<Period> periods){
        //如果closedAt为空 将period设置为最后一个
        if(closedAt == null){
            return periods.get(periods.size()-1);
        }
        //如果closedAt在period空隙
        if(isNotInPeriod(closedAt)){
            return getLastPeriodBeforeClosed(closedAt, periods);
        }

        for(Period period : periods){
            if(isInPeriod(closedAt,period)){
                return period;
            }
        }

        return null;
    }


    //找到创建时间（不在period内）之后的第一个period
    public Period getFirstPeriodAfterCreated(Date created,List<Period> periods){
        for(Period period : periods){
            if(getTimeOfPeriod(period).compareTo(created)>0){
                return period;
            }
        }
        return null;
    }


    //判断createdAt是否在第一个period之前
    public boolean isBeforeFirstPeriod(Date createdAt,Period period){
        if(getTimeOfPeriod(period).compareTo(createdAt)>0){
            return true;
        }
        return false;
    }


    //判断某个时间点是否在period的空隙里面
    public boolean isNotInPeriod(Date createdAt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdAt);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour <9 || (hour >= 12 && hour <13) || hour >17 ){
            return true;
        }
        return false;
    }


    //找到关闭时间（不在period内）前的最后一个period
    public Period getLastPeriodBeforeClosed(Date closedAt,List<Period> periods){
        for(int i=periods.size()-1;i>0;i--){
            if(getTimeOfPeriod(periods.get(i)).compareTo(closedAt) < 0){
                System.out.println("periods.get(i)"+periods.get(i));
                return periods.get(i);
            }
        }
        return null;
    }



    //获取本周里程碑
    public Milestone getMilestoneOfThisWeek(){
        //获取所有的里程碑

        return null;
    }

    //获取assignee本周里程碑下的gitRets
    public List<GitResult> getIssueRetOfThisMilestone(Milestone milestone,String assignee){

        return null;
    }

    //获取某个assigne的gitRets  1 close状态 并且 close时间在周一到周五的 2 状态为open并且属于本周或者以前里程碑的
    public List<GitResult> getAllGitRetThisWeek(String assignee){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date dateStart = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        Date dateEnd = calendar.getTime();
        List<GitResult> gitResults = new ArrayList<>();
        gitResults.addAll(this.getClosedGitRet(assignee, dateStart,dateEnd));
        gitResults.addAll(this.selectGitRetDueOnThisWeek(this.getOpenGitRet(assignee)));
        return gitResults;
    }

    //统计某个assignee本周所有的GitRet
    public int getHoursInWork(List<GitResult> gitResults,List<Period> periods){
        int n = 0;
        for (GitResult gitResult : gitResults){
            Period startPeriod = getPeriodOfCreated(gitResult.getCreatedAt(),periods);
            Period endPeriod = getPreiodOfClosed(gitResult.getClosedAt(),periods);
            if(startPeriod.getNumber()>endPeriod.getNumber()){
                throw new RuntimeException("invalid parameter");
            }
            if(startPeriod.getNumber() == endPeriod.getNumber()){
                if(!startPeriod.getIsInWork()){
                    startPeriod.setIsInWork(true);
                    n++;
                }
                continue;
            }
            for(int i= startPeriod.getNumber();i<=endPeriod.getNumber();i++){
                if( !periods.get(i).getIsInWork()) {
                    periods.get(i).setIsInWork(true);
                    n++;
                }
            }
        }
        return n;
    }
}
