package com.incar.gitApi.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/15.
 */
@Entity
@Table(name = "g_user_account")
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 3176972228965536016L;
    private int id;
    private String username; //用户名
    private String password;//密码

    public UserAccount() {
    }

    @Column(name = "password", nullable = false, insertable = true, updatable = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "username", nullable = false, insertable = true, updatable = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
