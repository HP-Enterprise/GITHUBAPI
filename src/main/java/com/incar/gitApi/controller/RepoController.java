package com.incar.gitApi.controller;

import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.service.RepoService;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Administrator on 2016/7/19.
 */
@RestController
@RequestMapping(value ="/api")
public class RepoController {
    @Autowired
    private RepoService repoService;

    /**
     *  创建仓库
     * @param repository
     * @return
     */
    @RequestMapping(value ="/addRepository" ,method = RequestMethod.POST)
    public ObjectResult addRepository(@RequestBody Repository repository){
        Repository repo=  repoService.addRepository(repository);
        return  new ObjectResult("true",repo);
    }

    /**
     * 创建issue
     * @param issue
     * @return
     * @throws IOException
     */
    @RequestMapping(value ="/addIssue" ,method = RequestMethod.POST)
    public ObjectResult addIssue(@RequestParam(value ="repository",required = true) String repository,
                                 @RequestBody Issue issue)throws IOException{
        Issue issue1= repoService.addIssue( repository,issue);
        return new ObjectResult("true",issue1);
    }
}
