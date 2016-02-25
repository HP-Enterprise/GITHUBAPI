package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitCmd;
import com.incar.gitApi.repository.GitCmdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
@Service
public class GitCmdService {
    @Autowired
    private GitCmdRepository gitCmdRepository;
    public GitCmd save(GitCmd gitCmd){
        return gitCmdRepository.save(gitCmd);
    }
}
