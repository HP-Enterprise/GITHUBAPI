package com.incar.gitApi.controller;

import com.incar.gitApi.entity.AddIssue;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.IssueShow;
import com.incar.gitApi.service.GitResultService;
import com.incar.gitApi.service.MyIssueService;
import com.incar.gitApi.service.ObjectResult;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
@RestController
@RequestMapping(value = "/api")
public class MyIssueController {
    @Autowired
    private MyIssueService myIssueService;
    @Autowired
    private GitResultService gitResultService;

    /**
     * 新建Issue
     * @param issue
     * @param repository
     * @param token
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "/addIssue/{repository}/{organization}/{token}", method = RequestMethod.POST)
    public ObjectResult addIssue(@RequestBody Issue issue,@PathVariable("repository")String repository,
                                 @PathVariable("organization")String organization,
                                 @PathVariable("token")String token) throws IOException {

        Issue issue1 = myIssueService.addIssue(organization, repository, issue,token);
        return new ObjectResult("true", issue1);
    }

    /**
     * 更新issue
     * @param gitResult
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updateIssue/{token}", method = RequestMethod.POST)
   public ObjectResult updateIssue(@PathVariable("token") String token,
                                    @RequestBody GitResult gitResult) throws IOException {
        Issue issue=new Issue();
        issue.setNumber(gitResult.getIssueId());
        issue.setCreatedAt(gitResult.getCreatedAt());
        issue.setTitle(gitResult.getTitle());
        issue.setState(gitResult.getState());
        if(gitResult.getMilestone()!=null){
            Milestone milestone=new Milestone();
            milestone.setNumber(gitResult.getMilestone());
            issue.setMilestone(milestone);
        }
       if(gitResult.getLabels()!=null){
           List<Label> labels=new ArrayList<>();
           String str[]=gitResult.getLabels().split(",");
           for(int i=0;i<str.length;i++) {
               Label label = new Label();
               label.setName(str[i]);
               labels.add(label);
           }
           issue.setLabels(labels);
       }
       if(gitResult.getAssignee()!=null){
           User user=new User();
           user.setLogin(gitResult.getAssignee());
           issue.setAssignee(user);
       }

        Issue issue1 = myIssueService.editIssue(gitResult.getUser(), gitResult.getProject(), issue, token);
        return new ObjectResult("true", issue1);
    }

    /**
     * 更新本地issue
     * @param gitResult
     * @return
     */
    @RequestMapping(value = "/updateGitResult", method = RequestMethod.POST)
    public ObjectResult updateGitResult(@RequestBody GitResult  gitResult){
        int a=gitResultService.editeGitResult(gitResult);
        return new ObjectResult("true",a);
    }

    @RequestMapping(value = "/addLocationIssue/{repository}/{organization}",method = RequestMethod.POST)
     public ObjectResult addLocationIssue(@PathVariable("repository")String repository,
                                          @PathVariable("organization")String organization,
                                          @RequestBody Issue issue){
         gitResultService.saveNewGitResult(issue,repository,organization);
         return new ObjectResult("true","添加成功");
     }
    @RequestMapping(value = "/LIssueList")
    public ObjectResult page(
            @RequestParam(value = "repository", required = true) String repository,
            @RequestParam(value = "organization", required = true) String organization,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        Page<GitResult> page = gitResultService.findPageOfGiTesult(repository, organization,state, currentPage, pageSize);
        List<GitResult> gitResultList = page.getContent();
        response.addHeader("Page", String.valueOf(page.getNumber()) + 1);
        response.addHeader("Page-Count", String.valueOf(page.getTotalPages()));
        return new ObjectResult("true", gitResultList);
    }
}