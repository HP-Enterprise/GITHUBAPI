package com.incar.gitApi.controller;

import com.incar.gitApi.service.MyMilestoneService;
import com.incar.gitApi.service.ObjectResult;
import org.eclipse.egit.github.core.Milestone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
@RestController
@RequestMapping(value = "/api")
public class MyMilestoneController {
    @Autowired
    private MyMilestoneService myMilestoneService;

    /**
     * 查询某个仓库下的所有milestone
     *
     * @param repository
     * @param state
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/milestoneList", method = RequestMethod.GET)
    public ObjectResult getAllMilestone(@RequestParam(name = "repository", required = true) String repository,
                                        @RequestParam(value = "user", required = true) String user,
                                        @RequestParam(name = "state", required = false) String state) throws IOException {
        List<Milestone> milestoneList = myMilestoneService.getAllMiles(user, repository, state);
        return new ObjectResult("true", milestoneList);
    }

    /**
     * 为某个仓库添加一个milestone
     *
     * @param repository
     * @param user
     * @param milestone
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addMilestone", method = RequestMethod.POST)
    public ObjectResult addMilestone(@RequestParam(name = "repository", required = true) String repository,
                                     @RequestParam(value = "user", required = true) String user,
                                     @RequestBody Milestone milestone) throws IOException {
        Milestone milestone1 = myMilestoneService.addMilestone(user, repository, milestone);
        return new ObjectResult("true", milestone1);
    }

    /**
     * 按年添加52周的milestone
     *
     * @param repository
     * @param user
     * @param year
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addAllMilestones", method = RequestMethod.POST)
    public ObjectResult addAllMilestones(@RequestParam(name = "repository", required = true) String repository,
                                         @RequestParam(value = "user", required = true) String user,
                                         @RequestParam(value = "year", required = true) Integer year) throws IOException {
        List<Milestone> list = myMilestoneService.addAllMilestone(user, repository, year);
        return new ObjectResult("true", list);
    }

}
