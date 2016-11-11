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
    @RequestMapping(value = "/labelList/{token}", method = RequestMethod.GET)
    public ObjectResult getAllLabel(@RequestParam(value = "repository", required = false) String repository,
                                    @RequestParam(value = "organization", required = false) String organization,
                                    @PathVariable("token")String token) throws IOException {
        List<Label> labelList = myLabelService.getAllLabel(organization, repository,token);
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
    @RequestMapping(value = "/addLabel/{repository}/{organization}/{token}", method = RequestMethod.POST)
    public ObjectResult addLabel(@PathVariable("repository") String repository,
                                 @PathVariable("organization") String organization,
                                 @RequestBody Label label,
                                 @PathVariable("token")String token) throws IOException {
        Label label3=new Label();
        label3.setName(label.getName());
        label3.setColor(label.getColor().substring(1,7).toString());
        Label label1 = myLabelService.addLabel(organization, repository, label3,token);
        return new ObjectResult("true", label1);
    }

    /**
     * 生成常用的label
     *
     * @param repository
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addAllLabels/{organization}/{token}", method = RequestMethod.POST)
    public ObjectResult addAllLabels(@RequestBody Repository repository,
                                     @PathVariable("organization") String organization,
                                     @PathVariable("token")String token) throws IOException {
        List<Label> list = myLabelService.addAllLabel(organization, repository.getName().toString(),token);
        return new ObjectResult("true", list);
    }

    /**
     * 删除标签
     * @param repository 仓库名
     * @param name 标签名
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/deleteLabel/{repository}/{organization}/{name}/{token}")
    public ObjectResult deleteLabel(@PathVariable("repository")String repository,
                                    @PathVariable("organization") String organization,
                                    @PathVariable("name")String name,
                                    @PathVariable("token")String token)throws IOException{
        myLabelService.deleteLabel(organization,repository,name,token);
        return new ObjectResult("true","删除成功！");
    }

    /**
     * 修改标签
     * @param repository
     * @param name
     * @param label
     * @param token
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/editLabel/{repository}/{organization}/{name}/{token}", method = RequestMethod.POST)
    public ObjectResult editLabel(@PathVariable("repository") String repository,
                                  @PathVariable("organization") String organization,
                                  @PathVariable("name") String name,
                                  @RequestBody Label label,
                                  @PathVariable("token")String token) throws IOException {
        Label label3=new Label();
        label3.setName(label.getName());
        label3.setColor(label.getColor().substring(1, 7).toString());
        Label label1 = myLabelService.editLabel(organization,repository,name,label3,token);
        return new ObjectResult("true", label1);
    }
}
