package com.incar.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ct on 2016/2/19 0019.
 */
@Entity
@Table(name="g_result")
public class GitResult {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer issueId;

    @Column
    private String title;

    @Column
    private Integer state;

    @Column
    private String assignee;

    @Column
    private Integer milestone;

    @Column
    private Date createAt;

    @Column
    private Date updateAt;

    @Column
    private Date closedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Integer getMilestone() {
        return milestone;
    }

    public void setMilestone(Integer milestone) {
        this.milestone = milestone;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public GitResult() {
    }

    public GitResult(Integer issueId, String title, Integer state, String assignee, Integer milestone, Date createAt, Date updateAt, Date closedAt) {
        this.issueId = issueId;
        this.title = title;
        this.state = state;
        this.assignee = assignee;
        this.milestone = milestone;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.closedAt = closedAt;
    }
}
