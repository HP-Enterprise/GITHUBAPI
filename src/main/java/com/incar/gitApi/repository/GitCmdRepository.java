package com.incar.gitApi.repository;

import com.incar.gitApi.entity.GitCmd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ct on 2016/2/19 0019.
 */
@Repository
public interface GitCmdRepository extends CrudRepository<GitCmd,Integer> {
    List<GitCmd> findAll();
}