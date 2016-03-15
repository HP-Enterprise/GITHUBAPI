package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitCmd;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.jsonObj.Label;
import com.incar.gitApi.repository.GitCmdRepository;
import com.incar.gitApi.jsonObj.Issue;
import com.incar.gitApi.query.IssueQuery;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ct on 2016/2/19 0019.
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
        List<Label> labels = issue.getLabels();
        if(!labels.isEmpty()){
            String labelRet = "";
            for(int i = 0 ; i<labels.size(); i++) {
                labelRet += labels.get(i).getName() + ",";
                if (i == labels.size()-1) {
                    labelRet += labels.get(i).getName();
                }
            }
            gitResult.setLabels(labelRet);
        }

        String repUrl = issue.getRepository_url();
        if(repUrl != null) {
            Pattern pattern = Pattern.compile("repos/.+?/(.+)");
            Matcher matcher = pattern.matcher(repUrl);
            if(matcher.find()) {
                String project = matcher.group(1);
                gitResult.setProject(project);
            }
        }
        return gitResult;
    }

    public Set<GitResult> issuesToGitResults(List<Issue> issues){
        Set<GitResult> gitResults = new HashSet<>();
        for(Issue issue : issues)
            gitResults.add(issueToGitRet(issue));
        return gitResults;
    }

    @Transactional
    public void saveGitResult(Set<GitResult> gitResults){
        gitResultRepository.save(gitResults);
    }

    public List<GitResult> findAll(){
        return gitResultRepository.findAll();
    }

    public List<Issue> queryGitApi(){
        List<GitCmd> gitCmds = gitCmdRepository.findAll();
        List<Issue> issues =  issueQuery.executeMultiCmds(gitCmds);
        return issues;
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
