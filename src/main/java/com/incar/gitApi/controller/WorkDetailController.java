package com.incar.gitApi.controller;

import com.incar.gitApi.entity.WorkDetail;
import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.service.WorkDetailService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
@RestController
@RequestMapping(value ="/api")
public class WorkDetailController {
    @Autowired
    private WorkDetailService workDetailService;

    /**
     * 分页查询详细工作信息
     * @param userName 用户名
     * @param project 项目名
     * @param state  状态
     * @param week  周
     * @param month  月
     * @param year  年
     * @param currentPage  当前页
     * @param pageSize 每页数量
     * @param response  http响应
     * @return
     */
    @RequestMapping(value = "/workDetail")
    public ObjectResult page(
            @RequestParam(value = "userName",required = false)String userName,
            @RequestParam(value = "project",required = false)String project,
            @RequestParam(value = "state" ,required = false )String state,
            @RequestParam(value = "week",required = false)Integer week,
            @RequestParam(value = "month",required = false)Integer month,
            @RequestParam(value = "quarter",required = false)Integer quarter,
            @RequestParam(value = "year",required = false)Integer year,
            @RequestParam(value = "currentPage",required = false)Integer currentPage,
            @RequestParam(value = "pageSize",required = false)Integer pageSize,
            HttpServletResponse response){
        Page<WorkDetail> page= workDetailService.findPageOfWorkDetail(userName, project, state, week, month,quarter, year, currentPage, pageSize);
        List<WorkDetail> workDetailList= page.getContent();
        response.addHeader("Page",String.valueOf(page.getNumber())+1);
        response.addHeader("Page-Count",String.valueOf(page.getTotalPages()));
        return new ObjectResult("true",workDetailList);
    }
}
