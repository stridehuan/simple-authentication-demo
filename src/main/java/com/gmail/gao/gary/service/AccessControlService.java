package com.gmail.gao.gary.service;

import com.gmail.gao.gary.common.config.CommonConfig;
import com.gmail.gao.gary.common.exception.CommonRuntimeException;
import com.gmail.gao.gary.common.exception.InvalidTokenException;
import com.gmail.gao.gary.common.utils.EncryptUtil;
import com.gmail.gao.gary.datasource.DataSource;
import com.gmail.gao.gary.entity.User;
import com.gmail.gao.gary.entity.Result;
import com.gmail.gao.gary.entity.Token;

import java.util.Set;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 1:26 PM
 */
public class AccessControlService {

    private static AccessControlService instance = new AccessControlService();

    private DataSource dataSource;

    private CommonConfig commonConfig;

    private String pwdEncryptSeed;

    private String tokenEncryptSeed;

    private long tokenTimeoutThreshold;

    /**
     * singleton instance
     * @return
     */
    public static AccessControlService getInstance() {
        return instance;
    }

    private AccessControlService() {
        this.dataSource = DataSource.getInstance();
        this.commonConfig = CommonConfig.getInstance();
        this.pwdEncryptSeed = commonConfig.getStringValue(CommonConfig.PWD_ENCRYPT_SEED);
        this.tokenEncryptSeed = commonConfig.getStringValue(CommonConfig.TOKEN_ENCRYPT_SEED);
        this.tokenTimeoutThreshold = commonConfig.getLongValue(CommonConfig.TOKEN_TIMEOUT_THRESHOLD) * 1000L;
    }

    public Result<String> authenticate(String userName, String password) {
        User user = dataSource.queryUser(userName);

        // check if user exists
        if (user == null) {
            throw new CommonRuntimeException("user " + userName + " doesn't exist");
        }

        // check password
        String encodedPwd = EncryptUtil.encode(password, pwdEncryptSeed);
        if (!encodedPwd.equals(user.getPassword())) {
            throw new CommonRuntimeException("wrong password");
        }

        // authenticate passed generate token
        String tokenSeq = generateTokenSeq(userName);

        return Result.success(tokenSeq);
    }

    public Result<Boolean> checkRole(String token, String roleName) {
        // query user by token
        User user = queryUserByToken(token);
        if (user == null) {
            throw new InvalidTokenException("token user dosen't exist");
        }

        // check user associate to role
        boolean containsRole = user.getRoles().containsKey(roleName);

        return Result.success(containsRole);
    }

    public Result<Set<String>> queryAllRoles(String token) {
        // query user by token
        User user = queryUserByToken(token);
        if (user == null) {
            throw new InvalidTokenException("token user dosen't exist");
        }

        Set<String> roleSet = user.getRoles().keySet();

        return Result.success(roleSet);
    }

    /**
     * query user by token
     * 1. parse token
     * 2. check token is valid
     * 3. query user
     * @param tokenSeq
     * @return
     */
    private User queryUserByToken(String tokenSeq) {
        // parse and check token
        Token token = parseTokenSeq(tokenSeq);

        boolean timeout = checkTimeout(token);
        if (timeout) {
            throw new InvalidTokenException("token is timeout");
        }

        // query user
        String userName = token.getUserName();
        return dataSource.queryUser(userName);
    }

    /**
     * generate encrypted token sequence
     * @param userName
     * @return
     */
    private String generateTokenSeq(String userName) {
        Token token = new Token(userName, System.currentTimeMillis());
        String tokenSeq = Token.generateTokenSeq(token);
        String encryptedTokenSeq = EncryptUtil.encode(tokenSeq, tokenEncryptSeed);

        return encryptedTokenSeq;
    }

    /**
     * parse encrypted token sequence
     * @param tokenSeq
     * @return
     */
    private Token parseTokenSeq(String tokenSeq) {
        String decodedTokenSeq = EncryptUtil.decode(tokenSeq, tokenEncryptSeed);
        Token token = Token.parseTokenSeq(decodedTokenSeq);

        return token;
    }

    /**
     * check if the token is timeout
     * @param token
     * @return
     */
    private boolean checkTimeout(Token token) {
        long generatedTime = token.getGenerateTimeStamp();
        long currentTime = System.currentTimeMillis();

        return currentTime > generatedTime + tokenTimeoutThreshold;
    }
}
