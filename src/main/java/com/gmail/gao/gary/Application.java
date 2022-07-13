package com.gmail.gao.gary;

import com.gmail.gao.gary.common.processor.BusinessExecutor;
import com.gmail.gao.gary.common.proxy.FacadeInvocationHandler;
import com.gmail.gao.gary.datasource.DataSource;
import com.gmail.gao.gary.entity.Result;
import com.gmail.gao.gary.entity.User;
import com.gmail.gao.gary.facade.AccessControlFacade;
import com.gmail.gao.gary.facade.RoleFacade;
import com.gmail.gao.gary.facade.UserFacade;
import com.gmail.gao.gary.facade.impl.AccessControlFacadeImpl;
import com.gmail.gao.gary.facade.impl.RoleFacadeImpl;
import com.gmail.gao.gary.facade.impl.UserFacadeImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/12 6:44 PM
 */
public class Application{
    /**
     * singleton
     */
    private static Application instance = new Application();

    /**
     * facade instaces
     */
    private Map<Class, Object>  facadeMap = new HashMap<Class, Object>();

    /**
     * get singleton instance
     * @return
     */
    public static Application getInstance() {
        return instance;
    }

    private Application() {
        init();
    }

    synchronized private void init() {
        initFacades();

        replaceFacadeByProxy();
    }

    /**
     * init facade instances
     */
    private void initFacades() {
        generateFacadeInstance(UserFacade.class, new UserFacadeImpl());
        generateFacadeInstance(RoleFacade.class, new RoleFacadeImpl());
        generateFacadeInstance(AccessControlFacade.class, new AccessControlFacadeImpl());
    }

    private <T> void generateFacadeInstance(Class<T> clazz, T instance) {
        facadeMap.put(clazz, instance);
    }

    /**
     * replace facade instance by proxy
     * proxy will invoke method in business thread pool
     */
    private void replaceFacadeByProxy() {
        for (Class key : facadeMap.keySet()) {
            Object val = facadeMap.get(key);
            Object proxy = new FacadeInvocationHandler(val).getProxy();
            facadeMap.put(key, proxy);
        }
    }

    public  <T> T getFacade(Class<T> clazz) {
        return (T)facadeMap.get(clazz);
    }


    public static void main(String[] args) throws InterruptedException {
        Application application = Application.getInstance();

        DataSource dataSource = DataSource.getInstance();

        UserFacade userFacade = application.getFacade(UserFacade.class);
        AccessControlFacade accessControlFacade = application.getFacade(AccessControlFacade.class);

        Result res1 = userFacade.createUser("rolename", "123456");

        Result res2 = accessControlFacade.authenticate("rolename", "123456");

        User user = dataSource.queryUser("rolename");


        System.out.println("");

        System.exit(0);
    }





}
