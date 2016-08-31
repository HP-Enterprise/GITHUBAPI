package com.incar.gitApi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/25.
 */
@Entity
@Table(name="g_project")
public class Project {
    @Id
    @Column(nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,updatable = false)
    private String name;

    @Column
    private String description;

    @Column
    private Date createdAt;

    @Column
    private int openIssue;
    @Column
    private Boolean isPrivate;
    @Column
    private String organization;

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpenIssue() {
        return openIssue;
    }

    public void setOpenIssue(int openIssue) {
        this.openIssue = openIssue;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
