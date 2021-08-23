package cn.enjoyedu.prepare.proxy;

import cn.enjoyedu.prepare.proxy.normal.James;

/**
 * 静态代理测试
 */
public class Lison {

    public static void main(String[] args) {

//        IGetServant receptionist = new Receptionist();
//        receptionist.choice("喜欢的样子");
        IGetServant james = new James();
        james.choice("14号");

    }

}
