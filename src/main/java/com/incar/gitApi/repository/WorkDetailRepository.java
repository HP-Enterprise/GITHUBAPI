package com.incar.gitApi.repository;

import com.incar.gitApi.entity.WorkDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Administrator on 2016/7/9.
 */
@Repository
public interface WorkDetailRepository extends CrudRepository<WorkDetail,Integer> {
    @Query("select w from WorkDetail w where w.userName like ?1 and w.week= ?2 and w.year=?3")
    Page<WorkDetail> findPage(String userName,Integer week,Integer year,Pageable pageable);
   @Query("select w from WorkDetail w where (?1 is null or w.userName like ?1)and" +
           "(?2 is null or w.project like ?2)and " +
           "(?3 is null or w.state like ?3) and " +
           "(?4 is null or w.week = ?4) and " +
           "(?5 is null or w.month = ?5)and" +
           "(?6 is null or w.quarter = ?6)and "+
           "(?7 is null or w.year = ?7)")
   Page<WorkDetail> findPage(String userName,String project,String state,Integer week,Integer month,Integer quarter,Integer year,Pageable pageable);
    @Query("select w from WorkDetail w  where (?1 is null or w.userName like ?1)and" +
            "(?2 is null or w.project like ?2)and " +
            "(?3 is null or w.state like ?3) and " +
            "(?4 is null or w.week = ?4) and " +
            "(?5 is null or w.month = ?5)and" +
            "(?6 is null or w.quarter = ?6)and "+
            "(?7 is null or w.year = ?7)order by w.year asc")
    List<WorkDetail> findExcel(String userName,String project,String state,Integer week,Integer month,Integer quarter,Integer year);
    @Query("select w from WorkDetail w where w.userName=?1")
    List<WorkDetail> findAll(String userName);
}
