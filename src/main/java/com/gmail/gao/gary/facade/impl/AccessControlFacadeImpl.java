package com.gmail.gao.gary.facade.impl;

import com.gmail.gao.gary.entity.Result;
import com.gmail.gao.gary.facade.AccessControlFacade;
import com.gmail.gao.gary.service.AccessControlService;

import java.util.Set;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 10:03 PM
 */
public class AccessControlFacadeImpl implements AccessControlFacade {

    private AccessControlService accessControlService;

    public AccessControlFacadeImpl() {
        this.accessControlService = AccessControlService.getInstance();
    }

    @Override
    public Result<String> authenticate(String userName, String password) {
        return accessControlService.authenticate(userName, password);
    }

    @Override
    public Result<Boolean> checkRole(String token, String roleName) {
        return accessControlService.checkRole(token, roleName);
    }

    @Override
    public Result<Set<String>> queryAllRoles(String token) {
        return accessControlService.queryAllRoles(token);
    }
}
