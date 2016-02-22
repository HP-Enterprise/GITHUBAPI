package com.incar.gitApi.repository;

import com.incar.gitApi.entity.GitResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19 0019.
 */
@Repository
public interface GitResultRepository extends CrudRepository<GitResult,Integer> {

    List<GitResult> findAll();
}
