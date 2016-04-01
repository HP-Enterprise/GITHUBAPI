package com.incar.gitApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HTML静态页面
 */
@Controller
public class PageController {

    /**
     * 静态页面处理程序
     * 主页面
     * @return 返回HTML静态页面
     */
    @RequestMapping("/gitHubApi/**")
    public String forwardIndex() {
        return "forward:/index.html";
    }

}
