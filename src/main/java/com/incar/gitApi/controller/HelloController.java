package com.incar.gitApi.controller;

import com.incar.gitApi.service.GitResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/2/19 0019.
 */
@RestController
@RequestMapping(value="/api")
public class HelloController {
    private String hostname;

    private String username;

    private String password;

    @Value("${com.incar.server.hostname}")
    public void setHostname(String hostname){this.hostname = hostname;};

    @Value("${com.incar.server.username}")
    public void setUsername(String username){this.username = username;};

    @Value("${com.incar.server.password}")
    public void setPassword(String password){this.password = password;}

//    private GitResultRepository gitResultRepository;
//
//    @Autowired
//    public void setGitResultRepository(GitResultRepository gitResultRepository){
//        this.gitResultRepository = gitResultRepository;
//    }

    @Autowired
    private GitResultService gitResultService;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return hostname+username+password;
    }

    @RequestMapping(value = "/git",method = RequestMethod.GET)
    public String gitResultList(){
        return gitResultService.toString();
    }
}
