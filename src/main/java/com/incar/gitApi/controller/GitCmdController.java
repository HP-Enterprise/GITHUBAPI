package com.incar.gitApi.controller;

import com.incar.gitApi.entity.GitCmd;
import com.incar.gitApi.repository.GitCmdRepository;
import com.incar.gitApi.service.GitCmdService;
import com.incar.gitApi.util.ObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
@RestController
@RequestMapping(value="/api")
public class GitCmdController {
    @Autowired
    private GitCmdService gitCmdService;

    @RequestMapping(value = "/gitcmd",method = RequestMethod.POST)
    public ObjectResult createCmd(@RequestBody GitCmd gitCmd){
        gitCmd = gitCmdService.save(gitCmd);
        if(gitCmd!=null)
            return new ObjectResult("true","添加成功");
        else
            return new ObjectResult("false","添加失败");
    }
}
