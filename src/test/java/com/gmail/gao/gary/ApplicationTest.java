package com.gmail.gao.gary;

import com.gmail.gao.gary.datasource.DataSource;
import com.gmail.gao.gary.entity.Result;
import com.gmail.gao.gary.entity.Role;
import com.gmail.gao.gary.entity.User;
import com.gmail.gao.gary.facade.AccessControlFacade;
import com.gmail.gao.gary.facade.RoleFacade;
import com.gmail.gao.gary.facade.UserFacade;

import java.util.Set;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/14 1:26 AM
 */
public class ApplicationTest {

    private static Application application = Application.getInstance();
    private static UserFacade userFacade = application.getFacade(UserFacade.class);
    private static RoleFacade roleFacade = application.getFacade(RoleFacade.class);
    private static AccessControlFacade accessControlFacade = application.getFacade(AccessControlFacade.class);


    private static DataSource dataSource = DataSource.getInstance();

    private static String testUserName1 = "user1";
    private static String testUserPwd1 = "pwd1";
    private static String testRoleName1 = "role1";

    private static String testUserName2 = "user2";
    private static String testUserPwd2 = "pwd2";
    private static String testRoleName2 = "role2";

    private static String testWrongPwd = "11111";

    public static void main(String[] args) {
        int index = 1;
        int successCount = 0;
        int failedCount = 0;
        boolean result;

        result = TestUtil.execTest(ApplicationTest::testCreateAndDeleteUser, "testCreateAndDeleteUser", index ++);
        if (result) successCount++; else failedCount++;

        result = TestUtil.execTest(ApplicationTest::testCreateAndDeleteRole, "testCreateAndDeleteRole", index ++);
        if (result) successCount++; else failedCount++;

        result = TestUtil.execTest(ApplicationTest::testAddRoleToUser, "testAddRoleToUser", index ++);
        if (result) successCount++; else failedCount++;

        result = TestUtil.execTest(ApplicationTest::testAuthenticate, "testAuthenticate", index ++);
        if (result) successCount++; else failedCount++;

        result = TestUtil.execTest(ApplicationTest::testInvalidate, "testInvalidate", index ++);
        if (result) successCount++; else failedCount++;

        result = TestUtil.execTest(ApplicationTest::testCheckRole, "testCheckRole", index ++);
        if (result) successCount++; else failedCount++;

        result = TestUtil.execTest(ApplicationTest::testAllRoles, "testAllRoles", index ++);
        if (result) successCount++; else failedCount++;

        System.out.println("\n Total Result: success = " + successCount + ", fail = " + failedCount);

        System.exit(0);
    }

    public static boolean testCreateAndDeleteUser() {

        // create test user, check the data in dataSource
        userFacade.createUser(testUserName1, testUserPwd1);
        System.out.println("create userName = " + testUserName1 + ", userPwd = " + testUserPwd1);
        User userInDataSource = dataSource.queryUser(testUserName1);
        System.out.println("user in dataSource userName = " + userInDataSource.getName() + ", encrypted userPwd = " + userInDataSource.getPassword());

        // delete first time
        Result deleteRes1 = userFacade.deleteUser(testUserName1);
        System.out.println("delete user result = " + deleteRes1.toString());
        User userInDataSourceAfterDelete = dataSource.queryUser(testUserName1);
        System.out.println("user in dataSource = " + userInDataSourceAfterDelete);

        // delete second time, user not exist, result is expected to be failed
        Result deleteRes2 = userFacade.deleteUser(testUserName1);
        System.out.println("delete user result = " + deleteRes2.toString());

        return userInDataSource.getName().equals(testUserName1)
                && !userInDataSource.getPassword().equals(testUserPwd1)
                && deleteRes1.isSuccess()
                && userInDataSourceAfterDelete == null
                && !deleteRes2.isSuccess();
    }

    public static boolean testCreateAndDeleteRole() {
        // create test role, check the data in dataSource
        roleFacade.createRole(testRoleName1);
        System.out.println("create roleName = " + testRoleName1);
        Role roleInDataSource = dataSource.queryRole(testRoleName1);
        System.out.println("role in dataSource userName = " + roleInDataSource.getName());

        // delete first time
        Result deleteRes1 = roleFacade.deleteRole(testRoleName1);
        System.out.println("delete role result = " + deleteRes1.toString());
        Role roleInDataSourceAfterDelete = dataSource.queryRole(testRoleName1);
        System.out.println("role in dataSource = " + roleInDataSourceAfterDelete);

        // delete second time, role not exist, result is expected to be failed
        Result deleteRes2 = roleFacade.deleteRole(testRoleName1);
        System.out.println("delete role result = " + deleteRes2.toString());

        return roleInDataSource.getName().equals(testRoleName1)
                && deleteRes1.isSuccess()
                && roleInDataSourceAfterDelete == null
                && !deleteRes2.isSuccess();
    }

    public static boolean testAddRoleToUser() {
        boolean result = true;

        // create test user1 and user2
        userFacade.createUser(testUserName1, testUserPwd1);
        userFacade.createUser(testUserName2, testUserPwd2);

        // create test role1 and role2
        roleFacade.createRole(testRoleName1);
        roleFacade.createRole(testRoleName2);

        // add role1 to user1
        Result addRes1 = userFacade.addRole(testUserName1, testRoleName1);
        result = result && addRes1.isSuccess();

        // add role1 to user1 again
        Result addRes2 = userFacade.addRole(testUserName1, testRoleName1);
        result = result && addRes2.isSuccess();

        // add role2 to user1
        Result addRes3 = userFacade.addRole(testUserName1, testRoleName2);
        result = result && addRes3.isSuccess();

        // add role1 to user2
        Result addRes4 = userFacade.addRole(testUserName2, testRoleName1);
        result = result && addRes4.isSuccess();

        printUsersAndRoles();

        return result;
    }

    public static boolean testAuthenticate() {
        boolean result = true;

        // auth with right password
        Result authRes1 = accessControlFacade.authenticate(testUserName1, testUserPwd1);
        result = result && authRes1.isSuccess();
        System.out.println("authRes1 = " + authRes1.toString());

        // auth with wrong password
        Result authRes2 = accessControlFacade.authenticate(testUserName1, testWrongPwd);
        result = result && !authRes2.isSuccess();
        System.out.println("authRes2 = " + authRes2.toString());

        printUsersAndRoles();

        return result;
    }

    public static boolean testInvalidate() {
        boolean result = true;

        // auth with right password
        Result<String> authRes1 = accessControlFacade.authenticate(testUserName1, testUserPwd1);
        result = result && authRes1.isSuccess();
        String token = authRes1.getData();
        System.out.println("authRes1 = " + authRes1.toString());

        // check token is valid
        Result checkRoleRes1 = accessControlFacade.checkRole(token, testRoleName1);
        result = result && checkRoleRes1.isSuccess();
        System.out.println("checkRoleRes1 = " + checkRoleRes1.toString());

        // sleep to wait timeout
        System.out.println("sleep for 2 seconds");
        sleep(2000);

        // check token is timeout
        Result checkRoleRes2 = accessControlFacade.checkRole(token, testRoleName1);
        result = result && !checkRoleRes2.isSuccess();
        System.out.println("checkRoleRes2 = " + checkRoleRes2.toString());

        return result;
    }

    public static boolean testCheckRole() {
        boolean result = true;

        // auth user2 with right password, and return token in result
        Result<String> authRes1 = accessControlFacade.authenticate(testUserName2, testUserPwd2);
        result = result && authRes1.isSuccess();
        String token = authRes1.getData();
        System.out.println("authRes1 = " + authRes1.toString());

        // check token is valid, but user2 is not associated with role2
        Result<Boolean> checkRoleRes1 = accessControlFacade.checkRole(token, testRoleName2);
        result = result && checkRoleRes1.isSuccess() && !checkRoleRes1.getData();
        System.out.println("checkRoleRes1 = " + checkRoleRes1.toString());

        // check token is valid, and user2 is not associated with role1
        Result<Boolean> checkRoleRes2 = accessControlFacade.checkRole(token, testRoleName1);
        result = result && checkRoleRes2.isSuccess() && checkRoleRes2.getData();
        System.out.println("checkRoleRes2 = " + checkRoleRes2.toString());

        // sleep to wait timeout
        System.out.println("sleep for 2 seconds");
        sleep(2000);

        // check token is timeout
        Result<Boolean> checkRoleRes3 = accessControlFacade.checkRole(token, testRoleName1);
        result = result && !checkRoleRes3.isSuccess();
        System.out.println("checkRoleRes3 = " + checkRoleRes3.toString());

        return result;
    }

    public static boolean testAllRoles() {
        boolean result = true;

        // auth user1 with right password, and return token in result
        Result<String> authRes1 = accessControlFacade.authenticate(testUserName1, testUserPwd1);
        result = result && authRes1.isSuccess();
        String token1 = authRes1.getData();
        System.out.println("authRes1 = " + authRes1.toString());

        // auth user2 with right password, and return token in result
        Result<String> authRes2 = accessControlFacade.authenticate(testUserName2, testUserPwd2);
        result = result && authRes2.isSuccess();
        String token2 = authRes2.getData();
        System.out.println("authRes2 = " + authRes2.toString());

        // check token is valid
        Result<Set<String>> queryAllRolesRes1 = accessControlFacade.queryAllRoles(token1);
        result = result && queryAllRolesRes1.isSuccess();
        System.out.println("queryAllRolesRes1 = " + queryAllRolesRes1.toString());

        // check token is valid
        Result<Set<String>> queryAllRolesRes2 = accessControlFacade.queryAllRoles(token2);
        result = result && queryAllRolesRes2.isSuccess();
        System.out.println("queryAllRolesRes2 = " + queryAllRolesRes2.toString());

        // sleep to wait timeout
        System.out.println("sleep for 2 seconds");
        sleep(2000);

        // check token is valid
        Result<Set<String>> queryAllRolesRes3 = accessControlFacade.queryAllRoles(token2);
        result = result && !queryAllRolesRes3.isSuccess();
        System.out.println("queryAllRolesRes3 = " + queryAllRolesRes3.toString());

        return result;
    }


    private static void printUsersAndRoles() {
        System.out.println("------ data info: ");
        User user1InDataSource = dataSource.queryUser(testUserName1);
        System.out.println("user1 = " + user1InDataSource == null ? "null" : user1InDataSource.toString());
        User user2InDataSource = dataSource.queryUser(testUserName2);
        System.out.println("user2 = " + user2InDataSource == null ? "null" : user1InDataSource.toString());
        Role role1InDataSource = dataSource.queryRole(testRoleName1);
        System.out.println("role1 = " + role1InDataSource == null ? "null" : role1InDataSource.toString());
        Role role2InDataSource = dataSource.queryRole(testRoleName2);
        System.out.println("role2 = " + role2InDataSource == null ? "null" : role2InDataSource.toString());
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
