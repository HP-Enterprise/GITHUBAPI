package com.incar.gitApi.repository;

import com.incar.gitApi.entity.GitResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by ct on 2016/2/19 0019.
 */
@Repository
public interface GitResultRepository extends JpaRepository<GitResult,Integer>{

  //  List<GitResult> findAllGitResult();

    GitResult findByIssueId(int issueId);

    @Query( "select distinct g.assignee from GitResult g")
    List<String> findAllAssignee();

    @Query( "select distinct g.project from GitResult g")
    List<String> findAllProject();

    @Query( " select g from GitResult g where g.assignee=?1 and g.state = ?2 and  ( g.closedAt BETWEEN ?3 AND ?4)")
    List<GitResult> findClosedGitRet(String assignee, String state, Date weekStart, Date weekEnd);

    @Query( "select g from GitResult g where g.assignee = ?1 and g.state = ?2 and   g.createdAt < ?3  ")
    List<GitResult> findOpenGitRet(String assignee, String state, Date weekEnd);
    @Query( "select g from GitResult g where g.assignee = ?1")
    List<GitResult> findAllGitRet(String assignee);
    @Query("select distinct g from GitResult g where g.project=?1 and g.user=?2 and (?3 is null or g.state=?3)order by g.createdAt DESC")
    Page<GitResult> findPage(String project ,String user,String state,Pageable pageable);

    @Query( "select g from GitResult g where g.project=?1 and g.assignee = ?2 and g.state = ?3 and  g.createdAt< ?4 ")
    List<GitResult> findOpenTaskGit(String project,String assignee, String state, Date weekEnd);

    @Query( " select g from GitResult g where g.project=?1 and g.assignee=?2 and g.state = ?3 and (g.closedAt BETWEEN ?4 AND ?5)")
    List<GitResult> findClosedTaskGitt(String project ,String assignee, String state, Date weekStart, Date weekEnd);

    @Query( "select g from GitResult g where g.project=?1 and  g.assignee = ?2")
    List<GitResult> findAllTaskGit(String project,String assignee);

    @Modifying
    @Transactional
    @Query("update  GitResult  g  set g.state = ?1,g.title=?2,g.labels=?3,g.milestone=?4,g.assignee=?5 WHERE g.issueId = ?6 ")
    int modifyRitResult(String  state, String  title,String labels,Integer milestone,String assignee,Integer issueId);
}
