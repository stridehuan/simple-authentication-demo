package com.gmail.gao.gary.datasource.entry;

import java.util.HashSet;
import java.util.Set;

/**
 * Description: role entity
 * Author: huanbasara
 * Date: 2022/7/13 12:38 AM
 */
public class Role {
    private String name;

    private Set<String> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
        this.users = new HashSet<String>();
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

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }
}
