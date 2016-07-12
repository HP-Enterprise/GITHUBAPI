package com.incar.gitApi.repository;

import com.incar.gitApi.entity.GitResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface GitResultRepository extends JpaRepository<GitResult,Integer>{

  //  List<GitResult> findAllGitResult();

    GitResult findByIssueId(int issueId);

    @Query( "select distinct g.assignee from GitResult g")
    List<String> findAllAssignee();

    @Query( " select g from GitResult g where g.assignee=?1 and g.state = ?2 and  g.closedAt >?3 and  g.closedAt < ?4")
    List<GitResult>
    findClosedGitRet(String assignee, String state, Date closedAtStart, Date closedAtEnd);

    @Query( "select g from GitResult g where g.assignee = ?1 and g.state = ?2 and  (g.dueOn is null or g.dueOn <= ?3) ")
    List<GitResult> findOpenGitRet(String assignee, String state, Date dueOn);

}
