package cn.enjoyedu.prepare.proxy;

/**
 * 类说明：真实对象-前台小姐姐
 */
public class Receptionist implements IGetServant {
    public void choice(String desc) {
        System.out.println("为你 选择好了....."+desc);
    }
}
