package com.incar.gitApi.util;

import com.incar.gitApi.entity.GitResult;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
public class GitRetUtil {

    public static GitResult issueToGitRet(Issue issue){
        Assert.notNull(issue);
        GitResult gitResult = new GitResult();

        if(issue.getHtmlUrl().contains("/pull/")){
            return null;
        }
        String repUrl = issue.getUrl();
        if (repUrl != null) {
            Pattern pattern = Pattern.compile("repos/.+?/(.+?)/");
            Matcher matcher = pattern.matcher(repUrl);
            if (matcher.find()) {
                String project = matcher.group(1);
                gitResult.setProject(project);
            }
        }

        gitResult.setIssueId((int)issue.getId());
        gitResult.setAssignee(issue.getAssignee() == null ? null : issue.getAssignee().getLogin());
        gitResult.setClosedAt(issue.getClosedAt());
        gitResult.setCreatedAt(issue.getCreatedAt());
        gitResult.setUpdatedAt(issue.getUpdatedAt());
        gitResult.setMilestone(issue.getMilestone() == null ? null : issue.getMilestone().getNumber());
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

        return gitResult;
    }

    public static List<GitResult> IssuesToGitresults(List<Issue> issues){
        List<GitResult> gitResults = new ArrayList<>();
        for (Issue issue : issues){
            GitResult gitResult =  issueToGitRet(issue);
            if(gitResult != null)
               gitResults.add(gitResult);
        }
        return gitResults;
    }
}
