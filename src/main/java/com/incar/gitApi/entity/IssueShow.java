package com.incar.gitApi.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class IssueShow implements Serializable {
    private static final long serialVersionUID = 6358575015023539051L;
    private String title;
    private String body;
    private String assignee;
    private String sate;
    private Integer milestone;
    private List<String> labels;

    public IssueShow() {

    }

    public String getSate() {
        return sate;
    }

    public void setSate(String sate) {
        this.sate = sate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }


}
