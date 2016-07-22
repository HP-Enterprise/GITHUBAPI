package com.incar.gitApi.controller;

import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.service.RepoService;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
@RestController
@RequestMapping(value = "/api")
public class RepoController {
    @Autowired
    private RepoService repoService;

    /**
     * 创建个人仓库
     *
     * @param repository
     * @return
     */
    @RequestMapping(value = "/addRepository", method = RequestMethod.POST)
    public ObjectResult addRepository(@RequestBody Repository repository) {
        Repository repo = repoService.addRepository(repository);
        return new ObjectResult("true", repo);
    }

    /**
     * 创建组织仓库
     *
     * @param organization
     * @param repository
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addOrgRepository", method = RequestMethod.POST)
    public ObjectResult addOrgRepository(@RequestParam String organization, @RequestBody Repository repository) throws IOException {
        Repository repos = repoService.addOrgRepository(organization, repository);
        return new ObjectResult("true", repos);
    }

    /**
     * 查看组织所有仓库
     *
     * @param organization
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/repositoryList", method = RequestMethod.GET)
    public ObjectResult getAllRepository(@RequestParam(value = "organization", required = true) String organization) throws IOException {
        List<Repository> repositoryList = repoService.getAllRepository(organization);
        return new ObjectResult("true", repositoryList);
    }

}
