package com.incar.gitApi.controller;

import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.entity.UserAccount;
import com.incar.gitApi.service.*;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
@RestController
@RequestMapping(value = "/api")
public class LoginController {
    @Autowired
    private TokenService tokenService;


    /**
     * 用户登录
     * @param userAccount
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/loginGit",method = RequestMethod.POST)
    public ObjectResult loginGig(@RequestBody UserAccount userAccount,HttpServletResponse response)throws Exception{
        String username=userAccount.getUsername();
        String password=userAccount.getPassword();
        GitHubClient client=new GitHubClient();
        OrganizationService organizationService = new OrganizationService(client.setCredentials(username,password));
        List<User> users=  organizationService.getOrganizations();
        for (User user : users) {
          if(user.getLogin().equals("HP-Enterprise")){
              String  token=tokenService.generateToken(username);
              Cookie cookie = new Cookie("token",token);
              int seconds = 60*60*24;  //1天的秒数
              cookie.setMaxAge(seconds);  //cookie默认保存1天
              cookie.setPath("/"); //设置路径，这个路径即该工程下都可以访问该cookie  // 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
              response.addCookie(cookie);
              tokenService.saveToken(token, userAccount, seconds);
              UserAccount userAccount1=   tokenService.loadToken(token);
              return new ObjectResult("true","登录成功") ;
          }
        }
          return new ObjectResult("false","登录失败");
    }

    /**
     * 通过登录令牌获取登录信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/getLoginInfo/{token}",method = RequestMethod.GET)
    public ObjectResult getLoginInfo(@PathVariable("token")String token){
      UserAccount userAccount=  tokenService.loadToken(token);
        return new ObjectResult("true",userAccount);
    }

    /**
     * 注销并清除缓存
     * @param token
     * @return
     */
    @RequestMapping(value = "/loginOut/{token}")
    public ObjectResult loginOut(@PathVariable("token")String token){
      int a=  tokenService.eraseToken(token);
        return new ObjectResult("true",a);
    }
}
