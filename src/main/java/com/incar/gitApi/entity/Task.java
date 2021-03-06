package com.incar.gitApi.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Entity
@Table(name = "g_task")
public class Task {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private String username;

    @Column
    private Integer finishedWork;

    @Column
    private Integer unfinishedWork;

    @Column
    private Integer workHours;
    @Column
    private String project;
    @Column
    private Integer weekInYear;
    @Column
    private Integer year;
    @Column
    private String realname;
    @Column
    private String org;

    public Task() {
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFinishedWork() {
        return finishedWork;
    }

    public void setFinishedWork(Integer finishedWork) {
        this.finishedWork = finishedWork;
    }

    public Integer getUnfinishedWork() {
        return unfinishedWork;
    }

    public void setUnfinishedWork(Integer unfinishedWork) {
        this.unfinishedWork = unfinishedWork;
    }

    public Integer getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Integer workHours) {
        this.workHours = workHours;
    }

    public Integer getWeekInYear() {
        return weekInYear;
    }

    public void setWeekInYear(Integer weekInYear) {
        this.weekInYear = weekInYear;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", finishedWork=" + finishedWork +
                ", unfinishedWork=" + unfinishedWork +
                ", workHours=" + workHours +
                ", project='" + project + '\'' +
                ", weekInYear=" + weekInYear +
                ", year=" + year +
                ", realname='" + realname + '\'' +
                '}';
    }
}
