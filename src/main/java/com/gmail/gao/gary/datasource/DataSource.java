package com.gmail.gao.gary.datasource;

import com.gmail.gao.gary.common.exception.DataSourceProcessException;
import com.gmail.gao.gary.common.exception.DuplicatedKeyException;
import com.gmail.gao.gary.common.exception.InvalidEntityException;
import com.gmail.gao.gary.entity.Role;
import com.gmail.gao.gary.entity.User;

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
     * delete user by name
     * @param userName
     * @return affected rows
     */
    public int deleteUser(String userName) {
        User existedUser = userMap.remove(userName);

        if (existedUser == null) {
            return 0;
        } else {
            ConcurrentHashMap<String, Role> roles = existedUser.getRoles();
            for (Role role : roles.values()) {
                role.removeUser(userName);
            }

            return 1;
        }
    }

    /**
     * delete role by name
     * @param roleName
     * @return
     */
    public int deleteRole(String roleName) {
        Role existedRole = roleMap.remove(roleName);

        if (existedRole == null) {
            return 0;
        } else {
            ConcurrentHashMap<String, User> users = existedRole.getUsers();
            for (User user : users.values()) {
                user.removeRole(roleName);
            }

            return 1;
        }
    }

    /**
     * bind the association betweeen user and role
     * @param userName
     * @param roleName
     * @return
     */
    public int bindUserRole(String userName, String roleName) {
        User user = userMap.get(userName);
        Role role = roleMap.get(roleName);

        if (user == null) {
            throw new DataSourceProcessException("user " + userName + " dosen't exist");
        } else if (role == null) {
            throw new DataSourceProcessException("role " + roleName + " dosen't exist");
        } else {
            user.addRole(role);
            role.addUser(user);

            return 1;
        }
    }

    /**
     * unbind the association betweeen user and role
     * @param userName
     * @param roleName
     * @return
     */
    public int unbindUserRole(String userName, String roleName) {
        User user = userMap.get(userName);
        if (user != null) {
            user.removeRole(roleName);
        }

        Role role = roleMap.get(roleName);
        if (role != null) {
            role.removeUser(userName);
        }

        return 1;
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
}
