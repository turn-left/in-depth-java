package com.ethen.spring.context;


import com.ethen.spring.annotation.Component;
import com.ethen.spring.annotation.ComponentScan;
import com.ethen.spring.beans.BeanDefinition;
import com.ethen.spring.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * IDEA启动java程序时调用java命令详情
 * <p>
 * 类加载信息 -> 不同的classLoader加载不同路径的类
 * <p>
 * "C:\Program Files\Java\jdk1.8.0_181\bin\java.exe"
 * -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:49993,suspend=y,server=n
 * -javaagent:C:\Users\ethen\AppData\Local\JetBrains\IdeaIC2021.1\captureAgent\debugger-agent.jar
 * -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_181\jre\lib\charsets.jar;
 * C:\Program Files\Java\jdk1.8.0_181\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_181\jre\lib\ext\access-bridge-64.jar;
 * C:\Program Files\Java\jdk1.8.0_181\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_181\jre\lib\ext\dnsns.jar;
 * C:\Program Files\Java\jdk1.8.0_181\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_181\jre\lib\ext\jfxrt.jar;
 * 。。。
 * D:\source2021\in-depth-java\in-depth-spring\spring-source-demo\target\classes;
 * D:\soft2021\Maven\local_repo\org\springframework\spring-context\5.2.8.RELEASE\spring-context-5.2.8.RELEASE.jar;
 * D:\soft2021\Maven\local_repo\org\springframework\spring-aop\5.2.8.RELEASE\spring-aop-5.2.8.RELEASE.jar;
 * D:\soft2021\Maven\local_repo\org\springframework\spring-beans\5.2.8.RELEASE\spring-beans-5.2.8.RELEASE.jar;
 * D:\soft2021\Maven\local_repo\org\springframework\spring-core\5.2.8.RELEASE\spring-core-5.2.8.RELEASE.jar;
 * D:\soft2021\Maven\local_repo\org\springframework\spring-jcl\5.2.8.RELEASE\spring-jcl-5.2.8.RELEASE.jar;
 * D:\soft2021\Maven\local_repo\org\springframework\spring-expression\5.2.8.RELEASE\spring-expression-5.2.8.RELEASE.jar;
 * D:\soft2021\Maven\local_repo\org\projectlombok\lombok\1.18.18\lombok-1.18.18.jar;
 * D:\soft2021\Maven\local_repo\org\aspectj\aspectjrt\1.8.9\aspectjrt-1.8.9.jar;
 * D:\soft2021\Maven\local_repo\org\aspectj\aspectjweaver\1.8.9\aspectjweaver-1.8.9.jar;
 * D:\soft2021\IntelliJ IDEA Community Edition 2021.1.3\lib\idea_rt.jar"
 * com.ethen.loadbean.XmlLoadBeanTest
 * 。。。
 */
public class SimpleApplicationContext {
    private Class<?> configClass;
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    public SimpleApplicationContext(Class<?> configClass) {
        this.configClass = configClass;

        // 扫描
        doScan(configClass);
    }

    private void doScan(Class<?> configClass) {
        Objects.requireNonNull(configClass);
        List<String> classNameList = new ArrayList<>();
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
            String[] pkgs = componentScan.basePackages();
            assert (pkgs.length > 0);
            for (String pkg : pkgs) {
                String path = pkg.replaceAll("\\.", "/");
                ClassLoader classLoader = getClass().getClassLoader();
                URL resource = classLoader.getResource(path);
                assert resource != null;
                File file = new File(resource.getFile());
                scanClassFile(file, classNameList);
            }
            // 加载类
            if (classNameList.size() <= 0) {
                return;
            }
            final ClassLoader classLoader = getClass().getClassLoader();
            classNameList.forEach(className -> loadClass(className, classLoader));
        }
    }

    private void loadClass(String className, ClassLoader classLoader) {
        try {
            // 加载类
            Class<?> klass = classLoader.loadClass(className);
            // 是否为component
            if (klass.isAnnotationPresent(Component.class)) {
                Component comp = klass.getAnnotation(Component.class);
                String beanName = StringUtils.isEmpty(comp.value()) ? klass.getSimpleName() : comp.value();
                BeanDefinition bd = new BeanDefinition();
                bd.setKlass(klass);
                bd.setName(beanName);
                // scope是否为singleton
                // 是否为 Lazy Bean

            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void scanClassFile(File file, List<String> classNameList) {
        if (file.isDirectory()) {
            for (File fl : Objects.requireNonNull(file.listFiles())) {
                if (fl.isDirectory()) {
                    scanClassFile(fl, classNameList);
                } else {
                    addClassNameList(fl, classNameList);
                }
            }
        } else {
            addClassNameList(file, classNameList);
        }
    }

    private void addClassNameList(File classFile, List<String> classNameList) {
        String absolutePath = classFile.getAbsolutePath();
        String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class")).replaceAll("/", ".");
        classNameList.add(className);
    }

    public <T> T getBean(Class<T> beanClass) {
        return null;
    }
}
