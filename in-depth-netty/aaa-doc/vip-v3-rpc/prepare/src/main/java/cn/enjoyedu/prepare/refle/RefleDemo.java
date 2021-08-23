package cn.enjoyedu.prepare.refle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *往期课程咨询芊芊老师  QQ：2130753077  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示反射的使用
 */
public class RefleDemo {

	public static void main(String[] args) throws ClassNotFoundException, 
	InstantiationException, IllegalAccessException {

	    //实例化对象的标准用法，也就所谓的正
        Servant servant = new Servant();
        servant.service("Hello");

        //通过全限定名拿到类的class对象
		Class servantClazz = Class.forName("cn.enjoyedu.prepare.refle.Servant");

        //通过class对象拿到类的实例
		Servant servant1 = (Servant)servantClazz.newInstance();
		servant1.service("Hello");

        //通过class对象拿到方法列表
		Method[] methods = servantClazz.getDeclaredMethods();
		for(Method method:methods) {
			System.out.println("方法的名称:"+method.getName());//打印方法名称
			if(method.getName().equals("toString")){
				try {
				    //执行某个具体方法-toString
					System.out.println(method.getName()+"方法的执行结果:"
							+method.invoke(servantClazz.newInstance(),
							null));
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
