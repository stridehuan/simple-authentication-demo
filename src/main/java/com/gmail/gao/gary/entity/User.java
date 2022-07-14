package com.gmail.gao.gary.entity;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: UserEntity
 * Author: huanbasara
 * Date: 2022/7/13 12:29 AM
 */
public class User {
    /**
     * user name
     */
    private String name;

    /**
     * password（encrypted）
     */
    private String password;

    /**
     * associated roles
     */
    private ConcurrentHashMap<String, Role> roles;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.roles = new ConcurrentHashMap<String, Role>();
    }

    public void addRole(Role role) {
        roles.put(role.getName(), role);
    }

    public Role removeRole(String roleName) {
        return roles.remove(roleName);
    }

    @Override
    public String toString() {
        String rolesStr = "[";
        for (String role : roles.keySet()) {
            rolesStr = rolesStr + role + " ";
        }
        rolesStr = rolesStr + "]";

        return "name=" + name + ", password=" + password + ", roles=" + rolesStr;
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

    public ConcurrentHashMap<String, Role> getRoles() {
        return roles;
    }

    public void setRoles(ConcurrentHashMap<String, Role> roles) {
        this.roles = roles;
    }
}
