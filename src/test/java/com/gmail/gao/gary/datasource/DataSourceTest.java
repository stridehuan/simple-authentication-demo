package com.gmail.gao.gary.datasource;

import com.gmail.gao.gary.TestUtil;
import com.gmail.gao.gary.common.utils.EncryptUtilTest;
import com.gmail.gao.gary.entity.Role;
import com.gmail.gao.gary.entity.User;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/14 1:27 AM
 */
public class DataSourceTest {

    private static DataSource dataSource = DataSource.getInstance();

    public static void main(String[] args) {

        int index = 1;
        int successCount = 0;
        int failedCount = 0;
        boolean result;

        result = TestUtil.execTest(DataSourceTest::testInsertAndDeleteUser, "testEncodeAndDecode", index ++);
        if (result) successCount++; else failedCount++;

        result = TestUtil.execTest(DataSourceTest::testInsertAndDeleteRole, "testInsertAndDeleteRole", index ++);
        if (result) successCount++; else failedCount++;


        System.out.println("\n Total Result: success = " + successCount + ", fail = " + failedCount);

        System.exit(0);

    }

    public static boolean testInsertAndDeleteUser() {
        String userName = "a1";
        String password = "hgcd123";
        System.out.println("userName = " + userName + ", password = " + password);

        dataSource.insertUser(new User(userName, password));
        User user = dataSource.queryUser(userName);
        System.out.println("query userName = " + user.getName() + ", password = " + user.getPassword());

        dataSource.deleteUser(userName);
        User userAfterDelete = dataSource.queryUser(userName);
        System.out.println("after delete user, query result = " + userAfterDelete);

        return userName.equals(user.getName()) && password.equals(user.getPassword()) && userAfterDelete == null;
    }

    public static boolean testInsertAndDeleteRole() {
        String roleName = "a1";
        System.out.println("roleName = " + roleName );
        dataSource.insertRole(new Role(roleName));
        Role role = dataSource.queryRole(roleName);
        System.out.println("query roleName = " + role.getName());

        dataSource.deleteRole(roleName);
        Role roleAfterDelete = dataSource.queryRole(roleName);
        System.out.println("after delete role, query result = " + roleAfterDelete);

        return roleName.equals(role.getName()) && roleAfterDelete == null;

    }
}
