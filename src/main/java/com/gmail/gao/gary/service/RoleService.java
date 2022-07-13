package com.gmail.gao.gary.service;

import com.gmail.gao.gary.datasource.DataSource;
import com.gmail.gao.gary.entity.Role;
import com.gmail.gao.gary.entity.Result;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 1:25 PM
 */
public class RoleService {
    private static RoleService instance = new RoleService();

    /**
     * get singleton instance
     * @return
     */
    public static RoleService getInstance() {
        return instance;
    }

    private RoleService() {
        this.dataSource = DataSource.getInstance();
    }

    private DataSource dataSource;

    public Result createRole(String roleName) {

        dataSource.insertRole(new Role(roleName));

        return Result.success();
    }

    public Result deleteRole(String roleName) {
        int affectedRows = dataSource.deleteRole(roleName);

        if (affectedRows == 0) {
            return Result.failed("role named " + roleName + " dosen't exist");
        } else {
            return Result.success();
        }
    }


}
