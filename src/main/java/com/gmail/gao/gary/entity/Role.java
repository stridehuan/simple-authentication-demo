package com.gmail.gao.gary.entity;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: role entity
 * Author: huanbasara
 * Date: 2022/7/13 12:38 AM
 */
public class Role {
    /**
     * role name
     */
    private String name;

    /**
     * associated users
     */
    private ConcurrentHashMap<String, User> users;

    public Role(String name) {
        this.name = name;
        this.users = new ConcurrentHashMap<String, User>();
    }

    public void addUser(User user) {
        users.put(user.getName(), user);
    }

    public User removeUser(String userName) {
        return users.remove(userName);
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Role) {
            Role objRole = (Role) obj;
            if (this.name == null) {
                return objRole.name == null;
            } else {
                return this.name.equals(objRole.name);
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

    public ConcurrentHashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(ConcurrentHashMap<String, User> users) {
        this.users = users;
    }
}
