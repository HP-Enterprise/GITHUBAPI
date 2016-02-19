package com.incar.entity;

import javax.persistence.*;

/**
 * Created by ct on 2016/2/19 0019.
 */
@Entity
@Table(name="g_cmd")
public class GitCmd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String cmd;

    public GitCmd() {
    }

    public GitCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
