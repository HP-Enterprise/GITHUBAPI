package com.incar.gitApi.controller;

import com.incar.gitApi.entity.Project;
import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29.
 */
@RestController
@RequestMapping(value = "/api")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @RequestMapping(value = "/projectList")
    public ObjectResult page(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "currentPage",required = false)Integer currentPage,
            @RequestParam(value = "pageSize",required = false)Integer pageSize,
            HttpServletResponse response){
        Page<Project> page= projectService.findPageOfWorkDetail(name,currentPage,pageSize);
        List<Project> projectList= page.getContent();
        response.addHeader("Page",String.valueOf(page.getNumber())+1);
        response.addHeader("Page-Count",String.valueOf(page.getTotalPages()));
        return new ObjectResult("true",projectList);
    }
}
