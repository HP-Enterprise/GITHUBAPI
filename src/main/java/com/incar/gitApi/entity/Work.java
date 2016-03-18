package com.incar.gitApi.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Entity
@Table(name = "g_work")
public class Work {

    @Id
    @Column(nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,updatable = false)
    private String name;

    @Column
    private Integer finishedWork;

    @Column
    private Integer unfinishedWork;

    @Column
    private Integer workHours;

    @Column
    private Integer weekInYear;

    public Work(){}

    public Work(String name, Integer finishedWork, Integer unfinishedWork, Integer workHours, Integer weekInYear) {
        this.name = name;
        this.finishedWork = finishedWork;
        this.unfinishedWork = unfinishedWork;
        this.workHours = workHours;
        this.weekInYear = weekInYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
