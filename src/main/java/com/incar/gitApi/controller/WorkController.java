package com.incar.gitApi.controller;

import com.incar.gitApi.entity.Work;
import com.incar.gitApi.service.WorkService;
import com.incar.gitApi.service.ObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        Page<Work> page = workService.findPageOfWork(realname,username, weekInYear, currentPage, pageSize, fuzzy, orderByProperty, ascOrDesc);
        List<Work> workList = page.getContent();
        response.addHeader("Page",String.valueOf(page.getNumber())+1);
        response.addHeader("Page-Count",String.valueOf(page.getTotalPages()));
        return new ObjectResult("true",workList);
    }
}
