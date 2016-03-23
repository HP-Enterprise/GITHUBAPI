package com.incar.gitApi.repository;

import com.incar.gitApi.entity.GitResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ct on 2016/2/19 0019.
 */
@Repository
public interface GitResultRepository extends CrudRepository<GitResult,Integer> {


    Set<GitResult> findAll();

//    @Query("select g from GitResult g where g.closedAt > ?1 and g.state=?2")
//    Set<GitResult> findByClosedAt(Date closedAtStart,Date closedAtEnd);

    GitResult save(GitResult gitResult);

    GitResult findByIssueId(int issueId);

    @Query(value = "select g from GitResult g where " +
            "(?1 is null or g.issueId = ?1) " +
            " and (?2 is null or g.assignee = ?2) " +
            " and (?3 is null or g.state = ?3) " +
            " and (?4 is null or g.milestone = ?4) " +
            " and (?5 is null or g.title = ?5)  " +
            " and (?6 is null or g.createdAt > ?6) " +
            " and (?7 is null or g.createdAt < ?7) " +
            " and (?8 is null or g.closedAt > ?8)  " +
            " and (?9 is null or g.closedAt < ?9)")
    Page<GitResult> findByKeys(Integer issueId,String assignee,String state,Integer mileStone,String title,Date createdBegin,Date createdEnd,Date closedBegin,Date closedEnd,Pageable pageable);

    @Query(value = "select g from GitResult g where " +
            "(?1 is null or g.issueId = ?1) " +
            " and (?2 is null or g.assignee like concat('%',concat(?2,'%') ) ) " +
            " and (?3 is null or g.state like concat('%',concat(?3,'%') ) ) " +
            " and (?4 is null or g.milestone = ?4) " +
            " and (?5 is null or g.title like concat('%',concat(?5,'%') ) )  " +
            " and (?6 is null or g.createdAt > ?6) " +
            " and (?7 is null or g.createdAt < ?7) " +
            " and (?8 is null or g.closedAt > ?8)  " +
            " and (?9 is null or g.closedAt < ?9)")
    Page<GitResult> fuzzyFindByKeys(Integer issueId,String assignee,String state,Integer mileStone,String title,Date createdBegin,Date createdEnd,Date closedBegin,Date closedEnd,Pageable pageable);

    @Query( "select distinct g.assignee from GitResult g")
    List<String> findAllAssignee();

    @Query( " select g from GitResult g where g.assignee=?1 and g.state = ?2 and  g.closedAt >?3 and  g.closedAt < ?4")
    List<GitResult> findClosedGitRet(String assignee,String state ,Date closedAtStart,Date closedAtEnd);

    @Query( "select g from GitResult g where g.assignee = ?1 and g.state = ?2 and  g.dueOn <= ?3 ")
    List<GitResult> findOpenGitRet(String assignee,String state,Date dueOn);

}
