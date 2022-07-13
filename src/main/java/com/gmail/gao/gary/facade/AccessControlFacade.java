package com.gmail.gao.gary.facade;

import com.gmail.gao.gary.entity.Result;

import java.util.Set;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 10:02 PM
 */
public interface AccessControlFacade {

    Result<String> authenticate(String userName, String password);

    Result<Boolean> checkRole(String token, String roleName);

    Result<Set<String>> queryAllRoles(String token);
}
