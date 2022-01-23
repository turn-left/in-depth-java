package com.ethen.proxy.dynamic.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLib方法拦截器
 */
public class CgLibMethodInterceptor implements MethodInterceptor {

    /**
     * All generated proxied methods call this method instead of the original method.
     * The original method may either be invoked by normal reflection using the Method object,
     * or by using the MethodProxy (faster).
     *
     * @param obj    "this", the enhanced object 代理对象
     * @param method intercepted Method 被代理类中被拦截的接口方法
     * @param args   argument array; primitive types are wrapped 方法参数
     * @param proxy  used to invoke super (non-intercepted method); may be called
     *               as many times as needed
     * @return any value compatible with the signature of the proxied method. Method returning void will ignore this value.
     * @see MethodProxy
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.err.println("CgLibMethodInterceptor#intercept before method=" + method.toString());

        // fixme 分析底层调用逻辑？
        Object result = proxy.invokeSuper(obj, args);

        System.err.println("CgLibMethodInterceptor#intercept after ...");
        return result;
    }
}
