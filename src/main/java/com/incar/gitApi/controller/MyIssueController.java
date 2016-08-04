package com.incar.gitApi.controller;

import com.incar.gitApi.entity.AddIssue;
import com.incar.gitApi.entity.IssueShow;
import com.incar.gitApi.service.MyIssueService;
import com.incar.gitApi.service.ObjectResult;
import org.eclipse.egit.github.core.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/21.
 */
@RestController
@RequestMapping(value = "/api")
public class MyIssueController {
    @Autowired
    private MyIssueService myIssueService;

    /**
     * 添加issue
     * @param addIssue
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "/addIssue", method = RequestMethod.POST)
    public ObjectResult addIssue(@RequestBody AddIssue addIssue) throws IOException {
        System.out.println(addIssue.getRepository().getName().toString());
        System.out.println(addIssue.getIssue());
        Issue issue1 = myIssueService.addIssue("HP-Enterprise", addIssue.getRepository().getName().toString(), addIssue.getIssue());
        return new ObjectResult("true", issue1);
    }

    /**
     * 更新issue
     * @param repository
     * @param user
     * @param number
     * @param issueShow
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updateIssue",method = RequestMethod.POST)
    public ObjectResult updateIssue(@RequestParam(value = "repository", required = true) String repository,
                                    @RequestParam(value = "user", required = true) String user,
                                    @RequestParam(value = "number", required = true) Integer number,
                                    @RequestBody IssueShow issueShow)throws IOException{
        Issue issue1=  myIssueService.editIssue(user, repository,number, issueShow);
        return new ObjectResult("true",issue1);
    }
}
