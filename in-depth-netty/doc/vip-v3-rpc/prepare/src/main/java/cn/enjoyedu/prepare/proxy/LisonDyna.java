package cn.enjoyedu.prepare.proxy;

import cn.enjoyedu.prepare.proxy.dynamic.JamesDyna;

import java.lang.reflect.Proxy;
/**
 * 动态代理测试
 */
public class LisonDyna {
    public static void main(String[] args) {

        IGetServant james =(IGetServant)
                Proxy.newProxyInstance(IGetServant.class.getClassLoader(),
                        new Class[]{IGetServant.class},
                        new JamesDyna(new Receptionist()));
        james.choice("御姐范风格");
    }
}
