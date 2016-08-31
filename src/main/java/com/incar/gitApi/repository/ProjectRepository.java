package com.incar.gitApi.repository;

import com.incar.gitApi.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/8/29.
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project,Integer> {
    @Query("select p from Project p where( ?1 is null or p.name like ?1)")
    Page<Project> findPage(String name , Pageable pageable);
}
