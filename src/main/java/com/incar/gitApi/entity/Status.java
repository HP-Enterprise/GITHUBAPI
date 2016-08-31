package com.incar.gitApi.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public class Status {
    private List<Integer> states;

    public List<Integer> getStates() {
        return states;
    }

    public void setStates(List<Integer> states) {
        this.states = states;
    }

    @Override
    public String toString() {
        return "Status{" +
                "states=" + states +
                '}';
    }
}
