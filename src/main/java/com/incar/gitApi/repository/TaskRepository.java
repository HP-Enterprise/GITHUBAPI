package com.incar.gitApi.repository;

import com.incar.gitApi.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


/**
 * Created by Administrator on 2016/3/23 0023.
 */
@Repository
public interface TaskRepository extends CrudRepository<Task,Integer> {
    @Query("select distinct  t from Task t where t.project=?1 and (?2 is null or t.realname like ?2) and " +
            "(?3 is null or t.username like ?3) and " +
            "(?4 is null or t.weekInYear=?4) and " +
            "(?5 is null or t.year=?5)")
    Page<Task> findTaskPage(String project,String username,String realname,Integer weekInYear,Integer year,Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Task t where t.year=?1 and  t.weekInYear = ?2")
    int deleteByWeek(int year,int weekInYear);

}
