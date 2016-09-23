package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.util.DateUtil;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
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
    public Milestone addMilestone(String user,String repository,Milestone milestone,String token)throws IOException{
        MilestoneService milestoneService=new MilestoneService(githubClientConfig.getClient(token));
     Milestone milestone1=   milestoneService.createMilestone(user, repository, milestone);
        return milestone1;
    }

    /**
     * 生成某年52个周的milestone
     * @param repository
     * @return
     * @throws IOException
     */
    public List<Milestone> addAllMilestone(String user,String repository,String token)throws IOException{
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
                milestone.setDueOn(DateUtil.getWeekEnd(DateUtil.getYear(),i+1));
                Milestone milestone1=   this.addMilestone(user,repository,milestone,token);
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
    public void deleteMiles(String user,String repository,int number ,String token)throws IOException{
        MilestoneService milestoneService=new MilestoneService(githubClientConfig.getClient(token));
        milestoneService.deleteMilestone(user,repository,number);
    }
    public Milestone editMilestone(String org,String repository ,Milestone milestone,String token)throws IOException{
        MilestoneService milestoneService=new MilestoneService(githubClientConfig.getClient(token));
        IRepositoryIdProvider iRepositoryIdProvider = new IRepositoryIdProvider() {
            @Override
            public String generateId() {
                return org + "/" + repository;
            }
        };
     Milestone milestone1=    milestoneService.editMilestone(iRepositoryIdProvider, milestone);
        return milestone1;
    }
}
