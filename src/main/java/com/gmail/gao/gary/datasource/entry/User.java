package com.gmail.gao.gary.datasource.entry;

import java.util.HashSet;
import java.util.Set;

/**
 * Description: UserEntity
 * Author: huanbasara
 * Date: 2022/7/13 12:29 AM
 */
public class User {
    /**
     * 用户名
     */
    private String name;

    /**
     * 密码（已加密）
     */
    private String password;

    private Set<String> roles;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.roles = new HashSet<String>();
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User objUser = (User) obj;
            if (this.name == null) {
                return objUser.name == null;
            } else {
                return this.name.equals(objUser.name);
            }
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
