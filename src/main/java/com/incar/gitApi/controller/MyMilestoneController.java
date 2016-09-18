package com.incar.gitApi.controller;

import com.incar.gitApi.service.MyMilestoneService;
import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.util.DateUtil;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
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
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/milestoneList", method = RequestMethod.GET)
    public ObjectResult getAllMilestone(@RequestParam(value = "repository", required = true) String repository
                                       // @RequestParam(value = "user", required = true) String user,
                                      //  @RequestParam(name = "state", required = false) String state
    )throws IOException {
        List<Milestone> milestoneList = myMilestoneService.getAllMiles("HP-Enterprise", repository, "open");
        return new ObjectResult("true", milestoneList);
    }

    /**
     * 为某个仓库添加一个milestone
     *
     * @param repository
     * @param milestone
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addMilestone/{repository}/{token}", method = RequestMethod.POST)
    public ObjectResult addMilestone(@PathVariable("repository") String repository,
                                    @PathVariable("token")String token,
                                     @RequestBody Milestone milestone) throws IOException {
        Milestone milestone1 = myMilestoneService.addMilestone("HP-Enterprise", repository, milestone,token);
        return new ObjectResult("true", milestone1);
    }

    /**
     * 按年添加52周的milestone
     *
     * @param repository
     * @param token
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addAllMilestones/{token}", method = RequestMethod.POST)
    public ObjectResult addAllMilestones(@RequestBody Repository repository,
                                         @PathVariable("token")String token) throws IOException {
        List<Milestone> list = myMilestoneService.addAllMilestone("HP-Enterprise", repository.getName().toString(),token);
        return new ObjectResult("true", list);
    }

    /**
     * 删除里程碑
     * @param repository
     * @param number
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/deleteMiles/{repository}/{number}/{token}")
    public ObjectResult deleteMiles(@PathVariable("repository")String repository,
                                    @PathVariable("number")int number,
                                    @PathVariable("token")String token)throws IOException{
        myMilestoneService.deleteMiles("HP-Enterprise",repository,number,token);
        return new ObjectResult("true","删除成功！");
    }

    /**
     * 更新里程碑
     * @param repository 仓库名
     * @param milestone 里程碑实体
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/editMilestone/{repository}/{token}",method = RequestMethod.POST)
    public ObjectResult editMilestone(@PathVariable("repository")String repository,
                                      @RequestBody Milestone milestone,
                                      @PathVariable("token")String token)throws IOException{
      Milestone milestone1=  myMilestoneService.editMilestone("HP-Enterprise", repository, milestone,token);
        return new ObjectResult("true",milestone1);
    }

}
