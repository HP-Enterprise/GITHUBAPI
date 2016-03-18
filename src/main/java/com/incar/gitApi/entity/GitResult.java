package com.incar.gitApi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ct on 2016/2/19 0019.
 */
@Entity
@Table(name="g_result")
public class GitResult {

    @Column(nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Id
    @Column(nullable = false,updatable = false)
    private Integer issueId;

    @Column
    private String title;

    @Column
    private String state;

    @Column
    private String assignee;

    @Column
    private Integer milestone;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Date closedAt;

    @Column
    private String labels;

    @Column
    private String project;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public GitResult() {
    }

    public GitResult(Integer issueId, String title, String state, String assignee, Integer milestone, Date createdAt, Date updatedAt, Date closedAt, String labels, String project) {
        this.issueId = issueId;
        this.title = title;
        this.state = state;
        this.assignee = assignee;
        this.milestone = milestone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.labels = labels;
        this.project = project;
    }

    @Override
    public String toString() {
        return "\nGitResult{" +"\n"+
                "id=" + id +"\n"+
                ", issueId=" + issueId +"\n"+
                ", title='" + title + '\'' +"\n"+
                ", state='" + state + '\'' +"\n"+
                ", assignee='" + assignee + '\'' +"\n"+
                ", milestone=" + milestone +"\n"+
                ", createdAt=" + createdAt +"\n"+
                ", updatedAt=" + updatedAt +"\n"+
                ", closedAt=" + closedAt +"\n"+
                ", labels='" + labels + '\'' +"\n"+
                ", project='" + project + '\'' +"\n"+
                '}';
    }
}
