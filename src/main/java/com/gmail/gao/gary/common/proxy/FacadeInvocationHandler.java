package com.gmail.gao.gary.common.proxy;

import com.gmail.gao.gary.common.exception.CommonRuntimeException;
import com.gmail.gao.gary.common.processor.BusinessExecutor;
import com.gmail.gao.gary.entity.Result;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 10:41 PM
 */
public class FacadeInvocationHandler implements InvocationHandler {
    private Object target;

    private BusinessExecutor businessExecutor;

    public FacadeInvocationHandler(Object target) {
        super();
        this.target = target;
        this.businessExecutor = BusinessExecutor.getInstance();
    }

    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {

        // situations do not need proxy
        // 1. is execution by businessExecutor
        // 2. return type is not Result
        if (businessExecutor.isExecutingByCurrentExecutor() || !Result.class.isAssignableFrom(method.getReturnType())) {
            return method.invoke(target, args);
        }

        // use businessExecutor to invoke method
        Future<Result> future = businessExecutor.submit(new Callable<Result>() {
            public Result call() throws Exception {
                try {
                    return (Result)method.invoke(target, args);
                } catch (InvocationTargetException e) {
                    return Result.failed(e.getTargetException().getMessage());
                } catch (Throwable t) {
                    return Result.failed(t.getMessage());
                }
            }
        });

        return future.get();
    }

    /**
     * get proxy
     * @return
     * @throws Throwable
     */
    public Object getProxy() {
        return Proxy.newProxyInstance(FacadeInvocationHandler.class.getClassLoader(),
                this.target.getClass().getInterfaces(), this);
    }
}
