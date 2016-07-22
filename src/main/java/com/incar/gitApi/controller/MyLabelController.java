package com.incar.gitApi.controller;

import com.incar.gitApi.service.MyLabelService;
import com.incar.gitApi.service.ObjectResult;
import org.eclipse.egit.github.core.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
@RestController
@RequestMapping(value = "/api")
public class MyLabelController {
    @Autowired
    private MyLabelService myLabelService;

    /**
     * 查询某个仓库中的所有标签
     *
     * @param repository 仓库名
     * @param user       用户名或组织名
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/labelList ", method = RequestMethod.GET)
    public ObjectResult getAllLabel(@RequestParam(name = "repository", required = true) String repository,
                                    @RequestParam(value = "user", required = true) String user) throws IOException {
        List<Label> labelList = myLabelService.getAllLabel(user, repository);
        return new ObjectResult("true", labelList);
    }

    /**
     * 添加一个新标签
     *
     * @param repository
     * @param user
     * @param label
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addLabel ", method = RequestMethod.POST)
    public ObjectResult addLabel(@RequestParam(name = "repository", required = true) String repository,
                                 @RequestParam(value = "user", required = true) String user,
                                 @RequestBody Label label) throws IOException {
        Label label1 = myLabelService.addLabel(user, repository, label);
        return new ObjectResult("true", label1);
    }

    /**
     * 生成常用的label
     *
     * @param repository
     * @param user
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addAllLabels", method = RequestMethod.POST)
    public ObjectResult addAllLabels(@RequestParam(name = "repository", required = true) String repository,
                                     @RequestParam(value = "user", required = true) String user) throws IOException {
        List<Label> list = myLabelService.addAllLabel(user, repository);
        return new ObjectResult("true", list);
    }
}
