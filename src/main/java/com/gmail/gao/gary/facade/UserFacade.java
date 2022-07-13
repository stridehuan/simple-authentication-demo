package com.gmail.gao.gary.facade;

import com.gmail.gao.gary.entity.Result;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 10:00 PM
 */
public interface UserFacade {
    Result createUser(String userName, String password);

    Result deleteUser(String userName);

    Result addRole(String userName, String roleName);
}
