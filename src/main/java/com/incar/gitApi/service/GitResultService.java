package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.util.GitRetUtil;
import com.incar.gitApi.GithubClientConfig;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

/**
 * Created by ct on 2016/2/19 0019.
 */
@Service
public class GitResultService {


    private GithubClientConfig githubClientConfig;

    private GitResultRepository gitResultRepository;

    private Logger logger = LoggerFactory.getLogger(GitResultService.class);

    @Autowired
    public void setGithubClientConfig(GithubClientConfig githubClientConfig){
        this.githubClientConfig = githubClientConfig;
    }

    @Autowired
    public void setGitResultRepository(GitResultRepository gitResultRepository){
        this.gitResultRepository = gitResultRepository;
    }

    @Transactional
    public void saveGitResult(){
        List<Issue> issues = getAllIssues();
        List<GitResult> gitResults = getGitResult(issues);
        gitResultRepository.save(gitResults);
    }
    @Transactional
    public void saveNewGitResult(Issue issue,String repository,String organization){
        GitResult gitResult=new GitResult();
        gitResult.setTitle(issue.getTitle());
        gitResult.setState("open");
        gitResult.setUser(organization);
            StringBuffer buffer = new StringBuffer("0123456789");
            StringBuffer saltStr = new StringBuffer();
            Random random = new Random();
            int range = buffer.length();
            for(int i = 0;i < 6;i++){
                saltStr.append(buffer.charAt(random.nextInt(range)));
            }
        gitResult.setIssueId(Integer.parseInt(saltStr.toString()));
        gitResult.setCreatedAt(new Date());
        gitResult.setProject(repository);
        if(issue.getAssignee()!=null){
            gitResult.setAssignee(issue.getAssignee().getLogin());
        }
       if (issue.getMilestone()!=null){gitResult.setMilestone(issue.getMilestone().getNumber());}
        List<Label> labels = issue.getLabels();
        if(!labels.isEmpty()){
            String labelRet = "";
            for(int i = 0 ; i<labels.size(); i++) {
                if (i == labels.size()-1) {
                    labelRet += labels.get(i).getName();
                }else {
                    labelRet += labels.get(i).getName() + ",";
                }
            }
            gitResult.setLabels(labelRet);
        }
    gitResultRepository.save(gitResult);
    }

    public int editeGitResult(GitResult gitResult){
      return   gitResultRepository.modifyRitResult(gitResult.getState(),gitResult.getTitle(),gitResult.getLabels(),gitResult.getMilestone(),gitResult.getAssignee(),gitResult.getIssueId());

    }
    @Transactional
    public void deleteGitResult(){
        gitResultRepository.deleteAll();
    }

    public List<GitResult> findAll(){
        return gitResultRepository.findAll();
    }


    public List<Issue> getIssues(String user,String repository){
        Map<String,String> params = new HashMap<String,String>();
        params.put("state","all");
        IssueService issueService = new IssueService(githubClientConfig.getGitHubClient());
        List<Issue> issueList = new ArrayList<>();
        try {
            issueList = issueService.getIssues(user, repository, params);
        } catch (IOException e) {
            logger.error("connecting failed",e);
        }
        return issueList;
    }

    public List<Issue> getAllIssues(){
        List<Issue> issues = new ArrayList<>();
        List<String> repos = githubClientConfig.getRepos();
        for (String str : repos){
            String[] keyValue = str.split("/");
            issues.addAll(this.getIssues(keyValue[0], keyValue[1]));
        }
        return issues;
    }


    public List<GitResult> getGitResult(List<Issue> issues){
        return GitRetUtil.issuesToGitresults(issues);
    }

    public Page<GitResult> findPageOfGiTesult(String project,String organization,String state,Integer currentPage,Integer pageSize){
        currentPage=(currentPage==null||currentPage<=0)?1:currentPage;
        pageSize=(pageSize==null||pageSize<=0)?10:pageSize;
        Pageable pageable = new PageRequest(currentPage-1,pageSize,new Sort(Sort.Direction.DESC,"state"));
        Page<GitResult> workDetailPage= gitResultRepository.findPage(project,organization, state, pageable);
        return new PageImpl<GitResult>(workDetailPage.getContent(),pageable,workDetailPage.getTotalElements());
    }

}
