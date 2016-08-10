package com.incar.gitApi.repository;

import com.incar.gitApi.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by Administrator on 2016/3/23 0023.
 */
@Repository
public interface WorkRepository extends CrudRepository<Work,Integer> {

    @Query("select distinct w from Work w where (?1 is null or w.realname=?1) and (?2 is null or w.username=?2) and (?3 is null or w.weekInYear=?3)")
    Page<Work> findPage(String realname,String username,Integer weekInYear,Pageable pageable);

    @Query("select distinct  w from Work w where (?1 is null or w.realname like ?1) and (?2 is null or w.username like ?2) and (?3 is null or w.weekInYear=?3)")
    Page<Work> fuzzyFindPage(String realname,String username,Integer weekInYear,Pageable pageable);
    @Query("select distinct  w from Work w where (?1 is null or w.realname like ?1) and (?2 is null or w.username like ?2) and (?3 is null or w.weekInYear=?3)")
    List<Work>   findExcel(String realname,String username,Integer weekInYear);

    @Modifying
    @Transactional
    @Query("delete from Work w where w.weekInYear = ?1")
    int deleteByWeek(int weekOfYear);
}
