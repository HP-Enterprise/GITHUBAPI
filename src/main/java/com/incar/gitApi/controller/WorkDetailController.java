package com.incar.gitApi.controller;


import com.incar.gitApi.entity.WorkDetail;
import com.incar.gitApi.repository.WorkDetailRepository;
import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.service.WorkDetailService;

import com.incar.gitApi.util.MapUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/12.
 */
@RestController
@RequestMapping(value ="/api")
public class WorkDetailController {
    @Autowired
    private WorkDetailService workDetailService;
    @Autowired
    private WorkDetailRepository workDetailRepository;

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
        Page<WorkDetail> page= workDetailService.findPageOfWorkDetail(userName, project, state, week, month, quarter, year, currentPage, pageSize);
        List<WorkDetail> workDetailList= page.getContent();
        response.addHeader("Page",String.valueOf(page.getNumber())+1);
        response.addHeader("Page-Count",String.valueOf(page.getTotalPages()));
        return new ObjectResult("true",workDetailList);
    }






    /**
     * 导出详细工作的excel表格
     * @param response
     * @param userName 用户名
     * @param project 项目
     * @param state 状态
     * @param week 周
     * @param month 月
     * @param quarter 季度
     * @param year 年
     * @return
     */

    @ RequestMapping(value = "/workDetail/exportExcel" ,method = RequestMethod.GET)
    public ObjectResult exportWorkExcel(HttpServletResponse response,
                                        @RequestParam(value = "userName", required = false) String userName,
                                        @RequestParam(value = "project", required = false) String project,
                                        @RequestParam(value = "state", required = false) String state,
                                        @RequestParam(value = "week", required = false) Integer week,
                                        @RequestParam(value = "month", required = false) Integer month,
                                        @RequestParam(value = "quarter", required = false) Integer quarter,
                                        @RequestParam(value = "year", required = false) Integer year){
        HSSFWorkbook work= workDetailService.findWorkDetailToExcel(userName, project, state, week, month, quarter, year);
        response.setHeader("conent-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=Work" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            work.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ObjectResult("true", "导出成功");
    }

    /**
     * 返回生成折线图的json数据
     * @param userName 用户名
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/json")
    public  ObjectResult  getData( @RequestParam(value = "userName",required = true)String userName) throws ServletException, IOException{
        List<WorkDetail> list = workDetailRepository.findAll(userName);
        Map map= MapUtil.putToMap(list);
      return new ObjectResult("true",map);
    }
}
