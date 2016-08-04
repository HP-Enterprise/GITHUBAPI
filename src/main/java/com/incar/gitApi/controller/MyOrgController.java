package com.incar.gitApi.controller;

import com.incar.gitApi.service.MyOrgService;
import com.incar.gitApi.service.ObjectResult;
import org.eclipse.egit.github.core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
@RestController
@RequestMapping(value = "/api")
public class MyOrgController {
    @Autowired
    private MyOrgService myOrgService;

    /**
     * 查询该组织的成员
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/orgMembers",method = RequestMethod.GET)
    public ObjectResult getAllMembers(//@RequestParam(value = "organization")String organization
                                         )throws IOException{
      List<User> userList= myOrgService.getOrgMembers("HP-Enterprise");
        return new ObjectResult("true",userList);
    }

    /**
     * 查询该用户的所有组织名
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/orgNames",method = RequestMethod.GET)
    public ObjectResult getOrgNames()throws IOException{
      List<User> users=  myOrgService.getOrganization();
        return new ObjectResult("true",users);
    }
}
