package com.ethen.proxy.dynamic.cglib;

import com.ethen.common.model.Order;
import com.ethen.common.service.OrderService;
import com.ethen.common.service.impl.OrderServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.util.List;

public class CglibTest {

    @Test
    public void testInterceptor() {
        // 设置代理类class文件保存位置
        CgLibUtil.enableSaveProxyFiles("D:/tmp/created/cglib/");

        // 增强器用来创建代理类
        Enhancer enhancer = new Enhancer();
        // 指定被代理的类
        enhancer.setSuperclass(OrderServiceImpl.class);
        CgLibMethodInterceptor cgLibMethodInterceptor = new CgLibMethodInterceptor();
        enhancer.setCallback(cgLibMethodInterceptor);
        OrderService orderService = (OrderServiceImpl) enhancer.create();
        System.err.println("CGLib className=" + orderService.getClass().getSimpleName());

        // 调用代理类的方法
        List<Order> orderList = orderService.selectOrderById("cglib-123123123");
        System.err.println(orderList);

    }
}
