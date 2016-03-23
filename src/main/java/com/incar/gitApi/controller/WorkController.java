package com.incar.gitApi.controller;

import com.incar.gitApi.entity.Work;
import com.incar.gitApi.service.WorkService;
import com.incar.gitApi.util.ObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
@RestController
@RequestMapping(value="/api")
public class WorkController {

    @Autowired
    private WorkService workService;

    @RequestMapping(value="/work")
    public ObjectResult page(@RequestParam(value = "name",required = false)String name,
                             @RequestParam(value = "weekNum" ,required = false )Integer weekInYear,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             @RequestParam(value = "pageSize",required = false)Integer pageSize,
                             @RequestParam(value = "fuzzy",required = false)Integer fuzzy,
                             @RequestParam(value = "orderByProperty",required = false)String orderByProperty,
                             @RequestParam(value = "ascOrDesc",required = false)Integer ascOrDesc,
                             HttpServletResponse response){
        Page<Work> page = workService.findPageOfWork(name, weekInYear, currentPage, pageSize, fuzzy, orderByProperty, ascOrDesc);
        List<Work> workList = page.getContent();
        response.addHeader("Page",String.valueOf(page.getNumber())+1);
        response.addHeader("Page-Count",String.valueOf(page.getTotalPages()));
        return new ObjectResult("true",workList);
    }
}
