package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.util.DateUtil;
import com.incar.gitApi.util.GitRetUtil;
import com.incar.gitApi.util.GithubClientConfig;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    public void setGithubClientConfig(GithubClientConfig githubClientConfig){
        this.githubClientConfig = githubClientConfig;
    }

    @Autowired
    public void setGitResultRepository(GitResultRepository gitResultRepository){
        this.gitResultRepository = gitResultRepository;
    }

    @Transactional
    public void saveGitResult(List<GitResult> gitResults){
        gitResultRepository.save(gitResults);
    }

    public Set<GitResult> findAll(){
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
            e.printStackTrace();
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


    public Page<GitResult> findPage(Integer issueId,String assignee,String state,Integer mileStone,String title,String begin,String end,String begin1,String end1,Integer currentPage,Integer pageSize,Integer fuzzy,String orderByProperty,Integer ascOrDesc){
        currentPage = currentPage == null?1:currentPage;
        currentPage = currentPage <= 0?1:currentPage;
        pageSize = pageSize == null?10:(pageSize <= 0?10:pageSize);
        boolean isFuzzy = fuzzy == null?false:(fuzzy==1?true:false);
        assignee = assignee==""?null:assignee;
        issueId = issueId ==null ?null:issueId;
        String format = "yyyy-MM-dd";
        Date createdBegin = (begin == null)?null:DateUtil.parseDate(begin,format);
        Date createdEnd = ( end== null)?null:DateUtil.parseDate(begin, format);
        Date closeedBegin = (begin == null)?null:DateUtil.parseDate(begin, format);
        Date closeedEnd = ( end== null)?null:DateUtil.parseDate(begin, format);
        orderByProperty = orderByProperty ==null?"assignee":orderByProperty;
        ascOrDesc = (ascOrDesc==null?0:(ascOrDesc!=1?0:ascOrDesc));//1表示升序，其他表示降序
        Sort.Direction direction = ascOrDesc==1? Sort.Direction.ASC:Sort.Direction.DESC;
        Pageable pageRequest = new PageRequest(currentPage-1,pageSize,new Sort(orderByProperty));

        Page<GitResult> gitResultPage ;
        if(isFuzzy){
            gitResultPage = gitResultRepository.fuzzyFindByKeys(issueId, assignee, state, mileStone, title, createdBegin, createdEnd, closeedBegin, closeedEnd, pageRequest);
        }else {
            gitResultPage = gitResultRepository.findByKeys(issueId, assignee, state, mileStone, title, createdBegin, createdEnd, closeedBegin, closeedEnd, pageRequest);
        }
        return new PageImpl<GitResult>(gitResultPage.getContent(),pageRequest,gitResultPage.getTotalElements());
    }



}
