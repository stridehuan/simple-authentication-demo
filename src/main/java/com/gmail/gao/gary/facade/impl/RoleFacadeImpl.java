package com.gmail.gao.gary.facade.impl;

import com.gmail.gao.gary.entity.Result;
import com.gmail.gao.gary.facade.RoleFacade;
import com.gmail.gao.gary.service.RoleService;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 10:03 PM
 */
public class RoleFacadeImpl implements RoleFacade {

    private RoleService roleService;

    public RoleFacadeImpl() {
        this.roleService = RoleService.getInstance();
    }

    @Override
    public Result createRole(String roleName) {
        return roleService.createRole(roleName);
    }

    @Override
    public Result deleteRole(String roleName) {
        return roleService.deleteRole(roleName);
    }
}
