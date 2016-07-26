package com.incar.gitApi.controller;

import com.incar.gitApi.entity.IssueShow;
import com.incar.gitApi.service.MyIssueService;
import com.incar.gitApi.service.ObjectResult;
import org.eclipse.egit.github.core.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 在组织的仓库中创建issue
     *
     * @param repository 仓库名
     * @param user       组织名
     * @param issue      issue对象
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addIssue", method = RequestMethod.POST)
    public ObjectResult addIssue(@RequestParam(value = "repository", required = true) String repository,
                                 @RequestParam(value = "user", required = true) String user,
                                 @RequestBody Issue issue) throws IOException {
        Issue issue1 = myIssueService.addIssue(user, repository, issue);
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
