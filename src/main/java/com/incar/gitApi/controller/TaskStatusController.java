package com.incar.gitApi.controller;

import com.incar.gitApi.entity.Task;
import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.service.TaskStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
@RestController
@RequestMapping(value = "/api")
public class TaskStatusController {
    @Autowired
    private TaskStatService taskStatService;

    @RequestMapping(value="/taskList",method = RequestMethod.GET)
    public ObjectResult page(
            @RequestParam(value = "project",required = true)String project,
            @RequestParam(value = "org",required = true)String org,
            @RequestParam(value = "username",required = false)String username,
            @RequestParam(value = "realname",required = false)String realname,
            @RequestParam(value = "weekInYear" ,required = false )Integer weekInYear,
            @RequestParam(value = "year" ,required = false )Integer year,
            @RequestParam(value = "currentPage",required = false)Integer currentPage,
            @RequestParam(value = "pageSize",required = false)Integer pageSize,
            HttpServletResponse response){
        Page<Task> page = taskStatService.findPageOfTask(project ,org,username,realname, weekInYear,year, currentPage, pageSize);
        List<Task> taskList = page.getContent();
        response.addHeader("Page",String.valueOf(page.getNumber())+1);
        response.addHeader("Page-Count",String.valueOf(page.getTotalPages()));
        return new ObjectResult("true",taskList);
    }

}
