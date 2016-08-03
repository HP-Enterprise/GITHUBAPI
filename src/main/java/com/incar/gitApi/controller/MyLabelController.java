package com.incar.gitApi.controller;

import com.incar.gitApi.service.MyLabelService;
import com.incar.gitApi.service.ObjectResult;
import org.eclipse.egit.github.core.Label;
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
public class MyLabelController {
    @Autowired
    private MyLabelService myLabelService;

    /**
     * 查询某个仓库的所有标签
     * @param repository 仓库
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/labelList", method = RequestMethod.GET)
    public ObjectResult getAllLabel(@RequestParam(value = "repository", required = false) String repository) throws IOException {
        List<Label> labelList = myLabelService.getAllLabel("HP-Enterprise", repository);
        return new ObjectResult("true", labelList);
    }

    /**
     * 添加一个新标签
     *
     * @param repository
     * @param label
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addLabel/{repository}", method = RequestMethod.POST)
    public ObjectResult addLabel(@PathVariable("repository") String repository,
                               //  @RequestParam(value = "user", required = true) String user,
                                 @RequestBody Label label) throws IOException {
        Label label3=new Label();
        label3.setName(label.getName());
        label3.setColor(label.getColor().substring(1,7).toString());
        Label label1 = myLabelService.addLabel("HP-Enterprise", repository, label3);
        return new ObjectResult("true", label1);
    }

    /**
     * 生成常用的label
     *
     * @param repository
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addAllLabels", method = RequestMethod.POST)
    public ObjectResult addAllLabels(@RequestBody Repository repository) throws IOException {
        List<Label> list = myLabelService.addAllLabel("HP-Enterprise", repository.getName().toString());
        return new ObjectResult("true", list);
    }
}
