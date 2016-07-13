package com.incar.gitApi.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/9.
 */
@Entity
@Table(name="g_work_detail")
public class WorkDetail {
    @Id
    @Column(nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,updatable = false)
    private String userName;
    @Column
    private  String  realName;
    @Column
    private String  title;
    @Column
    private  Integer expectedTime;
    @Column
    private Integer actualTime;
    @Column
   private String  state;
    @Column
    private String project;
    @Column
    private Integer week;
    @Column
    private Integer efficiency;//-1表示低效率，0表示平衡，1表示高效率�
    @Column
    private Integer month;
    @Column
    private Integer quarter;
    @Column
    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public WorkDetail(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Integer expectedTime) {
        this.expectedTime = expectedTime;
    }

    public Integer getActualTime() {
        return actualTime;
    }

    public void setActualTime(Integer actualTime) {
        this.actualTime = actualTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    @Override
    public String toString() {
        return "WorkDetail{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", title='" + title + '\'' +
                ", expectedTime=" + expectedTime +
                ", actualTime=" + actualTime +
                ", state='" + state + '\'' +
                ", project='" + project + '\'' +
                ", week=" + week +
                ", efficiency=" + efficiency +
                ", month=" + month +
                ", quarter=" + quarter +
                ", year=" + year +
                '}';
    }
}
