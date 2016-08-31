package com.incar.gitApi.service;


import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.entity.Project;
import com.incar.gitApi.repository.ProjectRepository;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29.
 */
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private GithubClientConfig githubClientConfig;

    public void saveProject()throws IOException{
        RepositoryService repositoryService = new RepositoryService(githubClientConfig.getGitHubClient());
        List<Repository> repositoryList= repositoryService.getOrgRepositories("HP-Enterprise");
        for (Repository repository : repositoryList) {
            Project project=new Project();
            project.setName(repository.getName());
            project.setDescription(repository.getDescription());
            project.setOpenIssue(repository.getOpenIssues());
            project.setCreatedAt(repository.getCreatedAt());
            project.setIsPrivate(repository.isPrivate());
            project.setOrganization(repository.getOwner().getLogin());
            projectRepository.save(project);
        }

    }

    public void deleteProject(){
        projectRepository.deleteAll();

    }
    public Page<Project> findPageOfWorkDetail(String name,Integer currentPage,Integer pageSize){
        currentPage=(currentPage==null||currentPage<=0)?1:currentPage;
        pageSize=(pageSize==null||pageSize<=0)?10:pageSize;
        if(name!=null){name="%"+name+"%";}
        Pageable pageable = new PageRequest(currentPage-1,pageSize,new Sort(Sort.Direction.DESC,"name"));
        Page<Project> projectPage= projectRepository.findPage(name,pageable);
        return new PageImpl<Project>(projectPage.getContent(),pageable,projectPage.getTotalElements());
    }
}
