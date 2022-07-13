package com.gmail.gao.gary.datasource;

import com.gmail.gao.gary.common.exception.DataSourceProcessException;
import com.gmail.gao.gary.common.exception.DuplicatedKeyException;
import com.gmail.gao.gary.common.exception.InvalidEntityException;
import com.gmail.gao.gary.datasource.entry.Role;
import com.gmail.gao.gary.datasource.entry.User;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: data source
 * Author: huanbasara
 * Date: 2022/7/13 12:43 AM
 */
public class DataSource {

    private static DataSource instance = new DataSource();

    private ConcurrentHashMap<String, User> userMap;

    private ConcurrentHashMap<String, Role> roleMap;

    /**
     * singleton instance
     * @return
     */
    public static DataSource getInstance() {
        return instance;
    }

    private DataSource () {
        userMap = new ConcurrentHashMap<String, User>();
        roleMap = new ConcurrentHashMap<String, Role>();
    }

    /**
     * query user by name
     * @param name
     * @return
     */
    public User queryUser(String name) {
        return userMap.get(name);
    }

    /**
     * query role by name
     * @param name
     * @return
     */
    public Role querRole(String name) {
        return roleMap.get(name);
    }

    /**
     * insert user
     * @param user
     * @return
     */
    public boolean insertUser(User user) {
        validate(user);

        User existedUser = userMap.putIfAbsent(user.getName(), user);

        if (existedUser == null) {
            return true;
        } else {
            throw new DuplicatedKeyException("user named " + user.getName() + " already exists");
        }
    }

    /**
     * insert role
     * @param role
     * @return
     */
    public boolean insertRole(Role role) {
        validate(role);

        Role existedRole = roleMap.putIfAbsent(role.getName(), role);

        if (existedRole == null) {
            return true;
        } else {
            throw new DuplicatedKeyException("role named " + role.getName() + " already exists");
        }
    }

    /**
     * update user
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
        User existedUser = userMap.get(user.getName());

        if (existedUser == null) {
            throw  new DataSourceProcessException("user named " + user.getName() + " dosen't exist");
        } else {
            copyFields(user, existedUser);
            return true;
        }

//        // delete user may happens at the same time, so lock the user instance and double check
//        synchronized (existedUser) {
//            // double check
//            if (userMap.get(user.getName()) == null) {
//                throw  new DataSourceProcessException("user named " + user.getName() + " dosen't exist");
//            } else {
//                userMap.put(user.getName(), user);
//                return true;
//            }
//        }
    }

    /**
     * update role
     * @param role
     * @return
     */
    public boolean updateRole(Role role) {
        Role existedRole = roleMap.get(role.getName());

        if (existedRole == null) {
            throw  new DataSourceProcessException("user named " + role.getName() + " dosen't exist");
        } else {
            copyFields(role, existedRole);
            return true;
        }
    }

    /**
     * delete user by name
     * @param name
     * @return
     */
    public boolean deleteUser(String name) {
        User existedUser = userMap.remove(name);

        if (existedUser == null) {
            throw  new DataSourceProcessException("user named " + name + " dosen't exist");
        } else {
            return true;
        }

//        // update user may happens at the same tiem, so lock the user instance and double check
//        synchronized (existedUser) {
//            // double check
//            if (userMap.get(name) == null) {
//                throw  new DataSourceProcessException("user named " + name + " dosen't exist");
//            } else {
//                userMap.remove(name);
//                return true;
//            }
//        }
    }

    /**
     * delete role by name
     * @param name
     * @return
     */
    public boolean deleteRole(String name) {
        Role existedRole = roleMap.remove(name);

        if (existedRole == null) {
            throw  new DataSourceProcessException("role named " + name + " dosen't exist");
        } else {
            return true;
        }
    }


    /**
     * check if User instance is valid
     * @param user
     */
    private void validate(User user) {
        if (user == null) {
            throw new InvalidEntityException("user is null");
        } else if (user.getName() == null || user.getName().trim().equals("")) {
            throw new InvalidEntityException("user name is null");
        }
    }

    /**
     * check if Role instance is valid
     * @param role
     */
    private void validate(Role role) {
        if (role == null) {
            throw new InvalidEntityException("role is null");
        } else if (role.getName() == null || role.getName().trim().equals("")) {
            throw new InvalidEntityException("role name is null");
        }
    }

    /**
     * copy fileds
     * @param from
     * @param to
     */
    private void copyFields(User from, User to) {
        to.setName(from.getName());
        to.setPassword(from.getPassword());
        to.setRoles(from.getRoles());
    }

    /**
     * copy fileds
     * @param from
     * @param to
     */
    private void copyFields(Role from, Role to) {
        to.setName(from.getName());
        to.setUsers(from.getUsers());
    }

}
