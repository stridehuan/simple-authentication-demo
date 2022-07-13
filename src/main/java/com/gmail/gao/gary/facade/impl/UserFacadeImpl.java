package com.gmail.gao.gary.facade.impl;

import com.gmail.gao.gary.entity.Result;
import com.gmail.gao.gary.facade.UserFacade;
import com.gmail.gao.gary.service.UserService;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 10:03 PM
 */
public class UserFacadeImpl implements UserFacade {

    private UserService userService;

    public UserFacadeImpl() {
        this.userService = UserService.getInstance();
    }

    @Override
    public Result createUser(String userName, String password) {
        return userService.createUser(userName, password);
    }

    @Override
    public Result deleteUser(String userName) {
        return userService.deleteUser(userName);
    }

    @Override
    public Result addRole(String userName, String roleName) {
        return userService.addRole(userName, roleName);
    }
}
