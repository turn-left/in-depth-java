package cn.enjoyedu.prepare.proxy.normal;

import cn.enjoyedu.prepare.proxy.IGetServant;
import cn.enjoyedu.prepare.proxy.Receptionist;

/**
 * 类说明：代理者-静态代理
 */
public class James implements IGetServant {

    private IGetServant receptionist = new Receptionist();

    public void choice(String desc) {
        //do james sth
        receptionist.choice(desc);
        System.out.println("second half price!");
    }






}
