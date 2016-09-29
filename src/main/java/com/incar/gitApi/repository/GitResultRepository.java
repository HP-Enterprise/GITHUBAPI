package com.incar.gitApi.repository;

import com.incar.gitApi.entity.GitResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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
    @Query("select g from GitResult g where g.project=?1 and (?2 is null or g.state=?2)order by g.createdAt DESC")
    Page<GitResult> findPage(String project ,String state,Pageable pageable);

    @Query( "select g from GitResult g where g.project=?1 and g.assignee = ?2 and g.state = ?3 and  g.createdAt< ?4 ")
    List<GitResult> findOpenTaskGit(String project,String assignee, String state, Date weekEnd);

    @Query( " select g from GitResult g where g.project=?1 and g.assignee=?2 and g.state = ?3 and (g.closedAt BETWEEN ?4 AND ?5)")
    List<GitResult> findClosedTaskGitt(String project ,String assignee, String state, Date weekStart, Date weekEnd);

    @Query( "select g from GitResult g where g.project=?1 and  g.assignee = ?2")
    List<GitResult> findAllTaskGit(String project,String assignee);
}
