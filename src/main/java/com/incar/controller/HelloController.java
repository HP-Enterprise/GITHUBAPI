package com.incar.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
    public void setHostname(){};

    @Value("${com.incar.server.username}")
    public void setUsername(){};

    @Value("${com.incar.server.password}")
    public void setPassword(){}

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return hostname+username+password;
    }
}
