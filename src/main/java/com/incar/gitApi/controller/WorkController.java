package com.incar.gitApi.controller;

import com.incar.gitApi.entity.Work;
import com.incar.gitApi.entity.WorkDetail;
import com.incar.gitApi.service.WorkDetailService;
import com.incar.gitApi.service.WorkService;
import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
@RestController
@RequestMapping(value="/api")
public class WorkController {

    @Autowired
    private WorkService workService;
    @Autowired
    private WorkDetailService workDetailService;


    /**
     * 分页查询工作信息
     * @param username github账户
     * @param realname 真实名字
     * @param weekInYear 周数
     * @param currentPage 当前页
     * @param pageSize 每页数量
     * @param fuzzy 是否模糊查询  1 表示模糊查询 其他不是
     * @param orderByProperty 排序属性 默认为姓名
     * @param ascOrDesc 升序或降序 默认降序 1表示升序
     * @param response http响应
     * @return json结果
     */
    @RequestMapping(value="/work",method = RequestMethod.GET)
    public ObjectResult page(
                             @RequestParam(value = "realname",required = false)String realname,
                             @RequestParam(value = "username",required = false)String username,
                             @RequestParam(value = "weekNum" ,required = false )Integer weekInYear,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             @RequestParam(value = "pageSize",required = false)Integer pageSize,
                             @RequestParam(value = "fuzzy",required = false)Integer fuzzy,
                             @RequestParam(value = "orderByProperty",required = false)String orderByProperty,
                             @RequestParam(value = "ascOrDesc",required = false)Integer ascOrDesc,
                             HttpServletResponse response){
        Page<Work> page = workService.findPageOfWork(realname, username, weekInYear, currentPage, pageSize, fuzzy, orderByProperty, ascOrDesc);
        List<Work> workList = page.getContent();
        response.addHeader("Page",String.valueOf(page.getNumber())+1);
        response.addHeader("Page-Count",String.valueOf(page.getTotalPages()));
        return new ObjectResult("true",workList);
    }
    @RequestMapping(value = "/personalWorkDetail" ,method = RequestMethod.GET)
    public ObjectResult personalWorkDetailPage(
                                       @RequestParam(value = "userName",required = true)String userName,
                                       @RequestParam(value = "week",required = true)Integer week,
                                    //   @RequestParam(value = "year",required = false)Integer year,
                                       @RequestParam(value = "currentPage",required = false )Integer currentPage,
                                       @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                       HttpServletResponse response){
        Page<WorkDetail> personalWorkDetailPage= workDetailService.findPageOfWorkDetail(userName, week, DateUtil.getYear(), currentPage, pageSize);
        List<WorkDetail> personalWorkDetailList=  personalWorkDetailPage.getContent();
        response.addHeader("Page",String.valueOf(personalWorkDetailPage.getNumber())+1);
        response.addHeader("Page-Count",String.valueOf(personalWorkDetailPage.getTotalPages()));
        return new ObjectResult("true",personalWorkDetailList);
    }

    /**
     * 导出work的excel表格
     * @param response http响应
     * @param request http请求
     * @param realname 姓名
     * @param username 用户名
     * @param weekInYear 周
     * @return
     */
    @ RequestMapping(value = "/exportExcel" ,method = RequestMethod.GET)
    public ObjectResult exportWorkExcel(HttpServletResponse response, HttpServletRequest request,

                                        @RequestParam(value = "realname", required = false) String realname,
                                        @RequestParam(value = "username", required = false) String username,
                                        @RequestParam(value = "weekNum", required = false) Integer weekInYear){

        HSSFWorkbook work= workService.findWorkToExcel(realname, username, weekInYear);
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

}
