package com.incar.gitApi.controller;

import com.incar.gitApi.entity.UserAccount;
import com.incar.gitApi.service.ObjectResult;
import com.incar.gitApi.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/8/15.
 */
@RestController
@RequestMapping(value = "/api")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    /**
     * 登录验证
     * @param userAccount
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ObjectResult login(@RequestBody UserAccount userAccount){
        String username=userAccount.getUsername();
        String password=userAccount.getPassword();
      int flag=  userAccountService.login(username,password);
        if(flag==0){
            return new ObjectResult("true","登录成功");
        }
        if(flag==1){
            return new ObjectResult("false","用户名不存在");
        }
        if(flag==2){
            return new ObjectResult("false","密码错误");
        }
        return new ObjectResult("false","登陆失败");
    }


}
