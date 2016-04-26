package com.incar.gitApi.controller;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.service.GitResultService;
import com.incar.gitApi.service.ObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;
import java.util.List;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
@RestController
@RequestMapping(value="/api")
public class GitResultController {

    @Autowired
    private GitResultService gitResultService;

    /**
     * issue结果分页查询
     * @param issueId issueid
     * @param assignee 指派人
     * @param state 状态
     * @param mileStone 里程碑
     * @param title 标题
     * @param begin 开始时间
     * @param end 结束时间
     * @param begin1 开始时间1
     * @param end1 结束时间1
     * @param currentPage 当前页
     * @param pageSize 页数
     * @param fuzzy 是否模糊查询
     * @param orderByProperty 排序属性
     * @param ascOrDesc 升序或降序
     * @param response http响应
     * @return
     * @throws HTTPException
     */
    @RequestMapping(value = "/git/issue",method = RequestMethod.GET)
    public ObjectResult page(@RequestParam(value = "issueId",required = false)Integer issueId,
                                    @RequestParam(value = "assignee",required = false)String assignee,
                                    @RequestParam(value = "state",required = false)String state,
                                    @RequestParam(value = "mileStone",required = false)Integer mileStone,
                                    @RequestParam(value = "title",required = false)String title,
                                    @RequestParam(value = "begin",required = false)String begin,
                                    @RequestParam(value = "end",required = false)String end,
                                    @RequestParam(value = "begin1",required = false)String begin1,
                                    @RequestParam(value = "end1",required = false)String end1,
                                    @RequestParam(value = "currentPage",required = false)Integer currentPage,
                                    @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                    @RequestParam(value = "fuzzy",required = false)Integer fuzzy,
                                    @RequestParam(value = "orderByProperty",required = false)String orderByProperty,
                                    @RequestParam(value = "ascOrDesc",required = false)Integer ascOrDesc,
                                    HttpServletResponse response)throws HTTPException
    {
        Page<GitResult> gitResultPage = gitResultService.findPage(issueId,assignee,state,mileStone,title,begin,end,begin1,end1,currentPage,pageSize,fuzzy,orderByProperty,ascOrDesc);
        List<GitResult> gitResults = gitResultPage.getContent();
        if( !gitResults.isEmpty()){
            response.addHeader("Page",String.valueOf(gitResultPage.getNumber()+1));
            response.addHeader("Page-Count",String.valueOf(gitResultPage.getTotalPages()));
        }
        return new ObjectResult("true",gitResults);
    }
}
