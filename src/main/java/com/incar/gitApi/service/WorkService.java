package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.Work;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.util.GithubClientConfig;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        //get all assignee
        List<String> assignees = gitResultRepository.findAllAssignee();
        Date date1 = null;
        Date date2 = null;
        //根据周数获取本周的开始时间和结束时间
        for (String assignee : assignees ){
            Work work = getWorkInfo(assignee,date1,date2);
        }
        return null;
    }

    //获取某个员工的工作信息
    public Work getWorkInfo(String assignee,Date date1,Date date2){
        Work work = new Work();
        //get finished work
        int finishedWork = this.getFinishedWork(assignee,date1,date2);
        work.setFinishedWork(finishedWork);
        //get unfinished work
        int unfinishedWork = this.getUnfinishedWork(assignee);
        work.setUnfinishedWork(unfinishedWork);
        //get workload
        return new Work();
    }


    public int taskDuration(List<GitResult> gitResults){
        for(GitResult gitResult : gitResults){
            String labels = gitResult.getLabels();
            if(labels!=null && gitResult.getLabels().matches("")){
                //get value
            }
        }
        return 0;
    }
    public int getFinishedWork(String assignee,Date date1 ,Date date2){
        List<GitResult> gitResults = gitResultRepository.findClosedIssue(assignee,"closed",date1,date2);

        return 0;
    }

    public int getUnfinishedWork(String assignee){
        List<GitResult> gitResults = gitResultRepository.findOpenIssue(assignee,"open");
        //判断这些ret是否是属于本周的里程碑
        for(GitResult gitResult : gitResults){
            int milestoneNum = gitResult.getMilestone();
            Date date = null;//本周最后一天的时间
//            milestoneService.getMilestone();
            MilestoneService milestoneService = new MilestoneService();
            Milestone milestone = null;
            //周统计截止时间和issue所属里程碑的截止时间相比，如果该时间小于里程碑的统计时间，表示issue属于下个里程碑，不用统计，大于或等于情况都要统计
            if(milestone.getDueOn().compareTo(date)==0){
                //不统计
            }

        }
        return 0;
    }
}
