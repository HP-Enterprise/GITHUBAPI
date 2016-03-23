package com.incar.gitApi.repository;

import com.incar.gitApi.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by Administrator on 2016/3/23 0023.
 */
@Repository
public interface WorkRepository extends CrudRepository<Work,Integer> {

    @Query("select w from Work w where (?1 is null or w.name=?1) and (?2 is null or w.weekInYear=?2)")
    Page<Work> findPage(String name,Integer weekInYear,Pageable pageable);

    @Query("select w from Work w where (?1 is null or w.name like ?1) and (?2 is null or w.weekInYear=?2)")
    Page<Work> fuzzyFindPage(String name,Integer weekInYear,Pageable pageable);

}
