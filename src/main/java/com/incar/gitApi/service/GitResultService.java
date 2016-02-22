package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitCmd;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.GitCmdRepository;
import com.incar.gitApi.jsonObj.Issue;
import com.incar.gitApi.query.IssueQuery;
import com.incar.gitApi.repository.GitResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/19 0019.
 */
@Service
public class GitResultService {

    @Autowired
    private IssueQuery issueQuery;

    @Autowired
    private GitResultRepository gitResultRepository;

    @Autowired
    private GitCmdRepository gitCmdRepository;

    public GitResult issueToGitRet(Issue issue){
        Assert.notNull(issue);
        GitResult gitResult = new GitResult();
        gitResult.setIssueId(issue.getId());
        gitResult.setAssignee(issue.getAssignee() == null ? null : issue.getAssignee().getLogin());
        gitResult.setClosedAt(issue.getClosed_at());
        gitResult.setCreatedAt(issue.getCreated_at());
        gitResult.setUpdatedAt(issue.getUpdated_at());
        gitResult.setMilestone(issue.getMilestone() == null ? null : issue.getMilestone().getId());
        gitResult.setState(issue.getState());
        gitResult.setTitle(issue.getTitle());
        return gitResult;
    }


    public List<GitResult> issuesToGitResults(List<Issue> issues){
        List<GitResult> gitResults = new ArrayList<>();
        for(Issue issue : issues)
            gitResults.add(issueToGitRet(issue));
        return gitResults;
    }




    @Transactional
    public void saveGitResult(List<GitResult> gitResults){
        gitResultRepository.save(gitResults);
    }

    public List<GitResult> findAll(){
        return gitResultRepository.findAll();
    }

    public List<GitResult> queryGitApi(){
        List<GitCmd> gitCmds = gitCmdRepository.findAll();
        List<Issue> issues =  issueQuery.executeMultiCmds(gitCmds);
        return issuesToGitResults(issues);
    }
}
