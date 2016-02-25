package com.incar.gitApi.jsonObj;

import java.util.Date;
import java.util.List;

/**
 * Created by ct on 2016/2/20 0020.
 */
public class Issue {
    private String url;
    private String repository_url;
    private String labels_url;
    private String comments_url;
    private String events_url;
    private String html_url;
    private Integer id;
    private Integer number;
    private String title;
    private User user;
    private List<Label> labels;
    private String state;
    private Boolean locked;
    private User assignee;
    private Integer comments;
    private Date created_at;
    private Date updated_at;
    private Date closed_at;
    private PullRequest pull_request;
    private String body;
    private Milestone milestone;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRepository_url() {
        return repository_url;
    }

    public void setRepository_url(String repository_url) {
        this.repository_url = repository_url;
    }

    public String getLabels_url() {
        return labels_url;
    }

    public void setLabels_url(String labels_url) {
        this.labels_url = labels_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(Date closed_at) {
        this.closed_at = closed_at;
    }

    public PullRequest getPull_request() {
        return pull_request;
    }

    public void setPull_request(PullRequest pull_request) {
        this.pull_request = pull_request;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "url='" + url + '\'' +
                ", repository_url='" + repository_url + '\'' +
                ", labels_url='" + labels_url + '\'' +
                ", comments_url='" + comments_url + '\'' +
                ", events_url='" + events_url + '\'' +
                ", html_url='" + html_url + '\'' +
                ", id=" + id +
                ", number=" + number +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", labels=" + labels +
                ", state='" + state + '\'' +
                ", locked=" + locked +
                ", assignee=" + assignee +
                ", comments=" + comments +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", closed_at=" + closed_at +
                ", pull_request=" + pull_request +
                ", body='" + body + '\'' +
                ", milestone='" + milestone + '\'' +
                '}';
    }
}
