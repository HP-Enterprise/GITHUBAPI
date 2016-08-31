package com.incar.gitApi.controller;

import com.incar.gitApi.entity.AddIssue;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.IssueShow;
import com.incar.gitApi.service.GitResultService;
import com.incar.gitApi.service.MyIssueService;
import com.incar.gitApi.service.ObjectResult;
import org.eclipse.egit.github.core.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
     * 添加issue
     *
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
     *
     * @param repository
     * @param user
     * @param number
     * @param issueShow
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updateIssue", method = RequestMethod.POST)
    public ObjectResult updateIssue(@RequestParam(value = "repository", required = true) String repository,
                                    @RequestParam(value = "user", required = true) String user,
                                    @RequestParam(value = "number", required = true) Integer number,
                                    @RequestBody IssueShow issueShow) throws IOException {
        Issue issue1 = myIssueService.editIssue(user, repository, number, issueShow);
        return new ObjectResult("true", issue1);
    }

    @RequestMapping(value = "/LIssueList")
    public ObjectResult page(
            @RequestParam(value = "repository", required = true) String repository,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        Page<GitResult> page = gitResultService.findPageOfGiTesult(repository, state, currentPage, pageSize);
        List<GitResult> gitResultList = page.getContent();
        response.addHeader("Page", String.valueOf(page.getNumber()) + 1);
        response.addHeader("Page-Count", String.valueOf(page.getTotalPages()));
        return new ObjectResult("true", gitResultList);
    }
}