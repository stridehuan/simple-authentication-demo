package com.gmail.gao.gary.service;

import com.gmail.gao.gary.common.config.CommonConfig;
import com.gmail.gao.gary.common.utils.EncryptUtil;
import com.gmail.gao.gary.datasource.DataSource;
import com.gmail.gao.gary.entity.User;
import com.gmail.gao.gary.entity.Result;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 1:24 PM
 */
public class UserService {
    private static UserService instance = new UserService();

    /**
     * singleton instance
     * @return
     */
    public static UserService getInstance() {
        return instance;
    }

    private UserService() {
        this.dataSource = DataSource.getInstance();
        this.commonConfig = CommonConfig.getInstance();
        this.pwdEncryptSeed = commonConfig.getStringValue(CommonConfig.PWD_ENCRYPT_SEED);
    }

    private DataSource dataSource;

    private CommonConfig commonConfig;

    private String pwdEncryptSeed;

    public Result createUser(String userName, String password) {
        String encodedPwd = EncryptUtil.encode(password, pwdEncryptSeed);

        boolean result = dataSource.insertUser(new User(userName, encodedPwd));

        return result ? Result.success() : Result.failed("create user failed");
    }

    public Result deleteUser(String userName) {
        int result = dataSource.deleteUser(userName);

        if (result == 0) {
            return Result.failed("user named " + userName + " dosen't exist");
        } else {
            return Result.success();
        }
    }

    public Result addRole(String userName, String roleName) {
        dataSource.bindUserRole(userName, roleName);

        return Result.success();
    }

}
