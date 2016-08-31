package com.incar.gitApi.controller;

import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.service.RepoService;
import org.eclipse.egit.github.core.*;
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
     * @param repository 仓库名
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addOrgRepository/{token}", method = RequestMethod.POST)
    public ObjectResult addOrgRepository(@RequestBody Repository repository,@PathVariable("token")String token) throws IOException {
        Repository repos = repoService.addOrgRepository("HP-Enterprise", repository, token);
        return new ObjectResult("true", repos);
    }

    /**
     * 查看组织所有仓库
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/repositoryList/{token}", method = RequestMethod.GET)
    public ObjectResult getAllRepository(@PathVariable("token")String token) throws IOException {
        List<Repository> repositoryList = repoService.getAllRepository("HP-Enterprise", token);
        return new ObjectResult("true", repositoryList);
    }

    /**
     * 更新仓库
     * @param repository 仓库对象
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updateOrgRepository/{name}/{token}",method = RequestMethod.POST)
    public  ObjectResult updateOrgRepository(@PathVariable("name")String name,
                                             @PathVariable("token")String token,
                                             @RequestBody Repository repository)throws IOException{
          Repository repository1= repoService.editRepository("HP-Enterprise", name, repository, token);
        return new ObjectResult("true",repository1);
    }

    /**
     * 删除仓库
     * @param repository 仓库名
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/deleteOrgRepository/{token}")
    public  ObjectResult deleteOrgRepository(//@RequestParam(value = "organization", required = true) String organization,
                                              @PathVariable("token")String token,
                                             @RequestParam(value = "repository", required = true) String repository)throws IOException{
         repoService.deleteRepository("HP-Enterprise",repository,token);
        return new ObjectResult("true","删除成功！");
    }

    /**
     * 获取项目的贡献
     * @param repository
     * @param token
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/contributorList/{repository}/{token}", method = RequestMethod.GET)
    public ObjectResult getRepositoryStaff(@PathVariable("repository")String repository,
                                           @PathVariable("token")String token) throws IOException {
        List<Contributor> contributorList = repoService.getcontributor("HP-Enterprise",repository ,token);
        return new ObjectResult("true", contributorList);
    }

    /**
     * 项目的分支
     * @param repository
     * @param token
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/repositoryBranch/{repository}/{token}", method = RequestMethod.GET)
    public ObjectResult getBranches(@PathVariable("repository")String repository,
                                           @PathVariable("token")String token) throws IOException {
        List<RepositoryBranch> branchList = repoService.getRepositoryBranch("HP-Enterprise", repository, token);
        return new ObjectResult("true", branchList);
    }
    @RequestMapping(value = "/repositoryCommit/{repository}/{token}", method = RequestMethod.GET)
    public ObjectResult getCommit(@PathVariable("repository")String repository,
                                    @PathVariable("token")String token) throws IOException {
        List<RepositoryCommit> commitList = repoService.getCommits("HP-Enterprise", repository, token);
        return new ObjectResult("true", commitList);
    }
}
