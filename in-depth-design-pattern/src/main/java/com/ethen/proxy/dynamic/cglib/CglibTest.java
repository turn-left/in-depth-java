package com.ethen.proxy.dynamic.cglib;

import com.ethen.common.model.GiaoGiao;
import com.ethen.common.model.Order;
import com.ethen.common.service.OrderService;
import com.ethen.common.service.impl.OrderServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.objenesis.ObjenesisStd;

import java.util.List;

public class CglibTest {
    @BeforeClass
    public static void setUp() {
        // 设置代理类class文件保存位置
        String location = "D:/tmp/created/cglib/";
        CgLibUtil.enableSaveProxyFiles(location);
    }

    @Test
    public void testInterceptor() {
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


    @Test
    public void testGiao() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(GiaoGiao.class);
        enhancer.setCallback(new MyMethodInterceptor());
        GiaoGiao proxy;
        // 方式一：通过Enhancer创建代理对象
        proxy = (GiaoGiao) enhancer.create();
        // 方式二：通过ObjenesisStd+cglib创建代理对象
//        ObjenesisStd objenesisStd = new ObjenesisStd(true);
//        proxy = objenesisStd.newInstance(GiaoGiao.class);
        System.err.println(proxy);

        // 使用代理对象调用目标方法
        proxy.sing("lalalalala");
        proxy.dance();
    }
}
