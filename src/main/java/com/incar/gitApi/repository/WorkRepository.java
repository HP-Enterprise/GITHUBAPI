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

    @Query("select distinct w from Work w where w.type=1 and " +
            "(?1 is null or w.realname=?1) and " +
            "(?2 is null or w.username=?2) and (?3 is null or w.weekInYear=?3) and " +
            "(?4 is null or w.weekYear=?4)")
    Page<Work> findPage(String realname,String username,Integer weekInYear,Integer weekYear,Pageable pageable);

    @Query("select distinct  w from Work w where w.type=1 and (?1 is null or w.realname like ?1) and" +
            " (?2 is null or w.username like ?2) and " +
            "(?3 is null or w.weekInYear=?3) and " +
            "(?4 is null or w.weekYear=?4)")
    Page<Work> fuzzyFindPage(String realname,String username,Integer weekInYear,Integer weekYear,Pageable pageable);

    @Query("select distinct w from Work w where w.type=2 " +
            "and (?1 is null or w.realname like ?1)" +
            " and (?2 is null or w.username like ?2)" +
            " and (?3 is null or w.weekInYear=?3)" +
            "and (?4 is null or w.weekYear=?4)")
    Page<Work> findMonthPage(String realname,String username,Integer weekInYear,Integer weekYear,Pageable pageable);

    @Query("select distinct w from Work w where w.type=3 " +
            "and (?1 is null or w.realname like ?1)" +
            " and (?2 is null or w.username like ?2) " +
            "and (?3 is null or w.weekInYear=?3) " +
            "and (?4 is null or w.weekYear=?4)")
    Page<Work> findQuarterPage(String realname,String username,Integer weekInYear,Integer weekYear,Pageable pageable);

    @Query("select distinct w from Work w where w.type=4 " +
            "and (?1 is null or w.realname like ?1) " +
            "and (?2 is null or w.username like ?2)" +
            " and (?3 is null or w.weekInYear=?3) " +
            "and (?4 is null or w.weekYear=?4)")
    Page<Work> findYearPage(String realname,String username,Integer weekInYear,Integer weekYear,Pageable pageable);

    @Query("select distinct  w from Work w where w.type=1 and (?1 is null or w.realname like ?1) and " +
            "(?2 is null or w.username like ?2) and " +
            "(?3 is null or w.weekInYear=?3)and " +
            "(?4 is null or w.weekYear=?4)")
    List<Work>   findExcel(String realname,String username,Integer weekInYear,Integer weekYear);

    @Query("select distinct  w from Work w where w.type=2 and (?1 is null or w.realname like ?1) and " +
            "(?2 is null or w.username like ?2) and " +
            "(?3 is null or w.weekInYear=?3) and " +
            "(?4 is null or w.weekYear=?4)")
    List<Work>   findMonthExcel(String realname,String username,Integer weekInYear,Integer weekYear);

    @Query("select distinct  w from Work w where w.type=3 and (?1 is null or w.realname like ?1) and " +
            "(?2 is null or w.username like ?2) and " +
            "(?3 is null or w.weekInYear=?3) and " +
            "(?4 is null or w.weekYear=?4)")
    List<Work>   findQuarterExcel(String realname,String username,Integer weekInYear,Integer weekYear);

    @Query("select distinct  w from Work w where w.type=4 and (?1 is null or w.realname like ?1) and " +
            "(?2 is null or w.username like ?2) and " +
            "(?3 is null or w.weekInYear=?3) and " +
            "(?4 is null or w.weekYear=?4)")
    List<Work>   findYearExcel(String realname,String username,Integer weekInYear,Integer weekYear);



    @Query("select  w from Work w where w.type=1and w.username=?1 and w.weekYear=?2 and( w.weekInYear between ?3 and ?4)")
    List<Work> findAll(String username,Integer year,Integer start,Integer end);

    @Modifying
    @Transactional
    @Query("delete from Work w where w.type=1 and  w.weekInYear = ?1 and  w.weekYear = ?2")
    int deleteByWeek(int weekOfYear,int weekYear);

    @Modifying
    @Transactional
    @Query("delete from Work w where w.type=2 and  w.weekInYear = ?1 and w.weekYear=?2")
    int deleteByMonth(int weekOfYear,int weekYear);

    @Modifying
    @Transactional
    @Query("delete from Work w where w.type=3 and  w.weekInYear = ?1 and w.weekYear=?2")
    int deleteByQuarter(int weekOfYear,int weekYear);

    @Modifying
    @Transactional
    @Query("delete from Work w where w.type=4 and  w.weekInYear = ?1")
    int deleteByYear(int weekOfYear);
}
