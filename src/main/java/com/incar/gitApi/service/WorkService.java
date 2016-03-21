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

//    private MilestoneService milestoneService = new MilestoneService(githubClientConfig.getGitHubClient());

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


    public List<GitResult> getClosedIssue(String assignee,Date date1 ,Date date2){
        return gitResultRepository.findClosedIssue(assignee, "closed", date1, date2);
    }


    public int getTotalFinishedWork(String assignee,Date date1 ,Date date2){
        return this.totalFinishedWork(getClosedIssue(assignee, date1, date2));
    }


    public int getUnfinishedWorkOfAssignee(String assignee){
        List<GitResult> gitResults = gitResultRepository.findOpenIssue(assignee, "open");
        return getTotalUnfinishedWork(gitResults);
    }


    public int getUnfinishedWorkOfOneIssue(GitResult gitResult){
        Milestone milestone = this.getMileStone(gitResult);
        int num = 0;
        if(isInThisWeek(milestone.getDueOn())) {
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


    public boolean isInThisWeek(Date dueon){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        Date date1 = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
        calendar.set(Calendar.HOUR,11);
        Date date2 = calendar.getTime();
        if(dueon.compareTo(date1)>0 && dueon.compareTo(date2)<=0){
            return true;
        }
        return false;
    }


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


    public List<Period> generatePeriods(){
        List<Period> periods = new ArrayList<>();
        for(int i=1;i<6;i++){
            for(int j=0 ; j<8 ; j++){
                Period period = new Period();
                period.setDayOfWeek(i);
                period.setNumber(j);
                period.setIsInWork(false);
                switch (j){
                    case 0:
                        period.setHourOfDay(9);
                        break;
                    case 1:
                        period.setHourOfDay(10);
                        break;
                    case 2:
                        period.setHourOfDay(11);
                        break;
                    case 3:
                        period.setHourOfDay(13);
                        break;
                    case 4:
                        period.setHourOfDay(14);
                        break;
                    case 5:
                        period.setHourOfDay(15);
                        break;
                    case 6:
                        period.setHourOfDay(16);
                        break;
                    case 7:
                        period.setHourOfDay(17);
                        break;
                    default:
                        throw new IllegalArgumentException("arg is invalid");
                }
                periods.add(period);
            }
        }
        return periods;
    }


    public List<Period> getWorkPeriodOfOneIssue(GitResult gitResult,List<Period> periods){
        Date createdAt = gitResult.getCreatedAt();
        Date closedAt = gitResult.getClosedAt();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdAt);
        int hourOfDayCreated = calendar.get(Calendar.HOUR_OF_DAY);
        int dayOfWeekCreated = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.setTime(closedAt);
        int hourOfDayClosed = calendar.get(Calendar.HOUR_OF_DAY);
        int dayOfWeekClosed = calendar.get(Calendar.DAY_OF_WEEK);

        for(int i = 0; i<periods.size();i++){
            int number = periods.get(i).getNumber();
            int periodDayOfWeek = periods.get(i).getDayOfWeek();
            int periodHourOfDay = periods.get(i).getHourOfDay();
            if(periodDayOfWeek == dayOfWeekCreated && periodHourOfDay == hourOfDayCreated){
                periods.get(i).setIsInWork(true);
                while (periods.get(++i).getDayOfWeek() <= dayOfWeekClosed){
                    if(periods.get(i).getHourOfDay() <= hourOfDayClosed){
                        periods.get(i).setIsInWork(true);
                    }
                }
                break;
            }
        }
        return periods;
    }
}
