package com.ethen.agent.javassist.chap01;

import com.ethen.agent.common.UserDao;
import javassist.*;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 使用javassist实现mybatis接口访问数据库
 * <p>
 * 动态生成类实现dao接口访问数据库
 */
public class MybatisJavassistImpl {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        String userDaoImplName = "com.ethen.MybatisJavassistImpl$UserDaoImpl";
        String targetMethodName = "selectAll";
        final ClassPool classPool = new ClassPool();
        classPool.insertClassPath(new LoaderClassPath(MybatisJavassistImpl.class.getClassLoader()));
        // 生成一个新的类 com.ethen.MybatisJavassistImpl$UserDaoImpl
        final CtClass targetClass = classPool.makeClass(userDaoImplName);
        final CtClass userDaoInterface = classPool.get(UserDao.class.getName());
        // 生成的类实现接口
        targetClass.addInterface(userDaoInterface);
        // 生成方法 selectAll
        String methodBody = "{" +
                "System.err.println(\"========================建立数据库连接======================\");" +
                "System.err.println(\"========================准备sql语句========================\");" +
//                "Class<?>[] interfaces = getClass().getInterfaces();" +
//                "try {" +
//                "    final Method method = interfaces[0].getDeclaredMethod(\"selectAll\");" +
//                "    final com.ethen.agent.annotation.Select annotation = method.getAnnotation(com.ethen.agent.annotation.Select.class);" +
//                "    final String sql = annotation.value();" +
//                "    System.err.println(\"========================准备sql语句========================\");" +
//                "    System.err.println(\"========================\" + sql + \"========================\");" +
//                "    System.err.println(\"========================执行完毕释放连接========================\");" +
//                "} catch (NoSuchMethodException e) {" +
//                "    e.printStackTrace();" +
//                "}" +
                "}";
        CtClass returnType = classPool.get(void.class.getName());
//        CtClass[] paramsTypes = new CtClass[]{classPool.get(void.class.getName())};
        final CtMethod targetMethod = new CtMethod(returnType, targetMethodName, null, targetClass);
        targetMethod.setBody(methodBody);
        targetClass.addMethod(targetMethod);
        // toClass -> 在JVM中加载生成Class对象
        final Class<?> klass = targetClass.toClass();
        final byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{klass});
        try {
            FileOutputStream out = new FileOutputStream("$Proxy0.class");
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final UserDao userDao = (UserDao) klass.newInstance();
        userDao.selectAll();
    }
}
