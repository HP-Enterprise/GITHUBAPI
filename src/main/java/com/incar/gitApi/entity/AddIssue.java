package com.incar.gitApi.entity;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;

/**
 * Created by Administrator on 2016/7/29.
 */
public class AddIssue {
    private Issue issue;
  private Repository repository;

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String toString() {
        return "AddIssue{" +
                "issue=" + issue +
                ", repository=" + repository +
                '}';
    }
}
