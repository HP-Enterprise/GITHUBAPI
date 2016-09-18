package com.incar.gitApi.controller;

import com.incar.gitApi.entity.Project;
import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.service.ProjectService;
import org.eclipse.egit.github.core.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29.
 */
@RestController
@RequestMapping(value = "/api")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    /**
     * 通过id更新项目
     * @param id
     * @param repository
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updateProject/{id}", method = RequestMethod.POST)
    public ObjectResult updateProject(@PathVariable("id") Integer id,
                                      @RequestBody Repository repository) throws IOException {
        int a = projectService.editProject(repository, id);
        return new ObjectResult("true", a);
    }

    /**
     * 添加项目
     * @param repository
     * @return
     */
    @RequestMapping(value = "/addProject",method = RequestMethod.POST)
    public ObjectResult addProject(@RequestBody Repository repository){
    Project project=   projectService.addProject(repository);
        return new ObjectResult("true",project);

    }

    /**
     * 根据项目名删除项目
     * @param name
     * @return
     */
    @RequestMapping(value = "/deleteProject/{name}")
    public ObjectResult deleteProject(@PathVariable("name")String name){
      int a=  projectService.deleteProject(name);
        return new ObjectResult("true",a);
    }

    /**
     * 项目分页列表
     * @param name
     * @param currentPage
     * @param pageSize
     * @param response
     * @return
     */
    @RequestMapping(value = "/projectList")
    public ObjectResult page(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        Page<Project> page = projectService.findPageOfWorkDetail(name, currentPage, pageSize);
        List<Project> projectList = page.getContent();
        response.addHeader("Page", String.valueOf(page.getNumber()) + 1);
        response.addHeader("Page-Count", String.valueOf(page.getTotalPages()));
        return new ObjectResult("true", projectList);
    }
}
