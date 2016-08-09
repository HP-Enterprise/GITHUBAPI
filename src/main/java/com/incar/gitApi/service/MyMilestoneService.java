package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.util.DateUtil;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.service.GistService;
import org.eclipse.egit.github.core.service.GitHubService;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Created by Administrator on 2016/7/20.
 */
@Service
public class MyMilestoneService {
    @Autowired
    private GithubClientConfig githubClientConfig;

    /**
     * 添加一个milestone
     * @param repository
     * @param milestone
     * @return
     * @throws IOException
     */
    public Milestone addMilestone(String user,String repository,Milestone milestone)throws IOException{
        MilestoneService milestoneService=new MilestoneService(githubClientConfig.getGitHubClient());
     Milestone milestone1=   milestoneService.createMilestone(user, repository, milestone);
        return milestone1;
    }

    /**
     * 生成某年52个周的milestone
     * @param repository
     * @param year
     * @return
     * @throws IOException
     */
    public List<Milestone> addAllMilestone(String user,String repository,Integer year)throws IOException{
        List<Milestone> list=new ArrayList<>();
        List<Milestone> list1=  this.getAllMiles(user, repository, null);
        List<Object> lis=new ArrayList<>();
        for (Milestone milestone : list1) {
           lis.add(milestone.getTitle());
        }
        for(int i=0;i<52;i++){
            String k= "W"+String.valueOf(i+1);
            if(!lis.toString().contains(k)){
                Milestone milestone=new Milestone();
                milestone.setTitle(k);
                milestone.setState("open");
                milestone.setNumber(i+1);
                milestone.setDueOn(DateUtil.getWeekEnd(year,i+1));
                Milestone milestone1=   this.addMilestone(user,repository,milestone);
                list.add(milestone1);
            }
        }
        return list;
    }

    /**
     * 查询某个仓库下的所有milestone
     * @param repository 仓库名
     * @param state  milestone的状态（open，close），可以为空
     * @return
     * @throws IOException
     */
    public List<Milestone> getAllMiles(String user,String repository,String state)throws IOException{
        MilestoneService milestoneService=new MilestoneService(githubClientConfig.getGitHubClient());
        List<Milestone> milestoneList =  milestoneService.getMilestones(user, repository, state);
        return milestoneList;
    }

    /**
     * 根据num查询某个仓库的一个milestone
     * @param repository 仓库名
     * @param num  编号
     * @return
     * @throws IOException
     */
    public Milestone getOneMiles(String user,String repository,int num)throws IOException{
        MilestoneService milestoneService=new MilestoneService(githubClientConfig.getGitHubClient());
       Milestone milestone =  milestoneService.getMilestone(user, repository, num);
        return milestone;
    }

    /**
     * 删除里程碑
     * @param user
     * @param repository
     * @param number
     * @throws IOException
     */
    public void deleteMiles(String user,String repository,int number )throws IOException{
        MilestoneService milestoneService=new MilestoneService(githubClientConfig.getGitHubClient());
        milestoneService.deleteMilestone(user,repository,number);
    }
    public Milestone editMilestone(String user,String repository ,Milestone milestone)throws IOException{
        GitHubService gitHubService=new GistService(githubClientConfig.getGitHubClient());
        StringBuilder uri = new StringBuilder("/repos");
        uri.append('/').append(user).append('/').append(repository);
        uri.append("/milestones");
        uri.append('/').append(milestone.getNumber());
        return (Milestone)gitHubService.getClient().post(uri.toString(), milestone, Milestone.class);
    }
}
