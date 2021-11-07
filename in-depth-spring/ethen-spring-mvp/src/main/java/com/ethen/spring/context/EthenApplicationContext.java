package com.ethen.spring.context;


import com.ethen.spring.annotation.*;
import com.ethen.spring.beans.BeanDefinition;
import com.ethen.spring.beans.BeanNameGenerator;
import com.ethen.spring.beans.BeanPostProcessor;
import com.ethen.spring.beans.InitializingBean;
import com.ethen.spring.exception.BeanNotExistException;

import java.io.File;
import java.lang.reflect.Field;
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
public class EthenApplicationContext {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private final Map<String, Object> singletonObjects = new HashMap<>(); // 并发控制 ？？
    private final List<BeanPostProcessor> postProcessorList = new ArrayList<>();

    public EthenApplicationContext(Class<?> configClass) {

        /* 扫描执行包，识别出Spring组件，生成Bean定义添加到Map中备用 */
        doScanObtainBeanDefinitionMap(configClass);

        /* 创建非懒加载singleton实例，并添加到单例池(一级缓存) */
        initSingletonObjects();
    }


    private void doScanObtainBeanDefinitionMap(Class<?> configClass) {
        Objects.requireNonNull(configClass);
        List<String> classNameList = new ArrayList<>();
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
            String[] pkgs = componentScan.basePackages();
            assert (pkgs.length > 0);
            for (String pkg : pkgs) {
                String path = pkg.replaceAll("\\.", "/");
                ClassLoader classLoader = getClass().getClassLoader();
                // 通过包的相对路径获取绝对路径
                URL resource = classLoader.getResource(path);
                assert resource != null;
                // 根据URL获取绝对路径文件
                File file = new File(resource.getFile());
                // 递归扫描该路径上的文件，获取该路径下的所有类的全限定名
                scanClassFile(file, classNameList);
            }
            // 加载类
            if (classNameList.size() <= 0) {
                return;
            }
            // 根据类名列表加载类，并解析成BeanDefinition添加到Map中备用
            ClassLoader classLoader = getClass().getClassLoader();
            classNameList.forEach(className -> loadBeanDefinition(className, classLoader));
        }
    }

    private void initSingletonObjects() {
        beanDefinitionMap.forEach((name, bd) -> {
            if (!bd.isSingleton()) {
                return;
            }
            final Object bean = createBean(name, bd);
            // fixme V1.0 since 2021/11/06 省略前后步骤
            if (bean != null) {
                singletonObjects.put(name, bean);
            }
        });
    }

    private void loadBeanDefinition(String className, ClassLoader classLoader) {
        try {
            // 加载类
            Class<?> klass = classLoader.loadClass(className);
            // 注解类不生成BeanDefinition
            if (klass.isAnnotation()) {
                return;
            }
            // 是否为component
            if (klass.isAnnotationPresent(Component.class)) {
                // fixme beanPostProcessorList 扩展点入口初始化 ！！
                if (BeanPostProcessor.class.isAssignableFrom(klass)) {
                    BeanPostProcessor postProcessor = (BeanPostProcessor) klass.newInstance();
                    postProcessorList.add(postProcessor);
                }
                String beanName = BeanNameGenerator.gen(klass);
                BeanDefinition bd = new BeanDefinition();
                bd.setKlass(klass);
                bd.setName(beanName);
                // scope是否为singleton
                if (klass.isAnnotationPresent(Scope.class)) {
                    Scope scope = klass.getAnnotation(Scope.class);
                    bd.setScope(scope.value());
                }
                // 是否为 Lazy Bean
                if (klass.isAnnotationPresent(Lazy.class)) {
                    bd.setLazy(true);
                }
                // 添加beanDefinition
                beanDefinitionMap.put(beanName, bd);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void scanClassFile(File file, List<String> classNameList) {
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
        String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class")).replaceAll("\\\\", ".");
        classNameList.add(className);
    }

    /**
     * 从Spring容器中获取bean
     * <p>
     * 1.singleton Bean直接从单例池(一级缓存中获取)
     * 2.缓存中没有触发Bean的创建
     * 3.singleton的创建后放到单例池中
     * 4.prototype每次返回新的对象
     */
    public <T> T getBean(String name) {
        if (!beanDefinitionMap.containsKey(name)) {
            throw new BeanNotExistException("bean name " + name + " not exist in then Spring context!");
        }
        // singleton
        BeanDefinition bd = beanDefinitionMap.get(name);
        if (bd.isSingleton()) {
            // 从缓存中获取单例
            Object singletonBean = singletonObjects.get(name);
            if (singletonBean == null) {
                singletonBean = createBean(name, bd);
            }
            return (T) singletonBean;
        } else {
            // prototype
            return (T) createBean(name, bd);
        }
    }

    /**
     * 从次方法获取的Bean就是一个完整可以使用的Spring组件
     * <p>
     * // 推断构造方法 (略)
     * // 实例化
     * // 初始化前
     * // 初始化 依赖注入 解决循环依赖(略)
     * // 初始化后 AOP
     */
    private Object createBean(String name, BeanDefinition bd) {
        Class<?> klass = bd.getKlass();
        try {
            Object instance = klass.newInstance();
            // 属性赋值
            final Field[] fields = klass.getDeclaredFields();
            for (Field fd : fields) {
                // 处理依赖注入
                if (fd.isAnnotationPresent(Autowired.class)) {
                    fd.setAccessible(true);
                    fd.set(instance, getBean(fd.getName()));
                }
            }
            /*
             * 初始化前
             * fixme 属性赋值扩展点 配置中心
             */
            for (BeanPostProcessor postProcessor : postProcessorList) {
                postProcessor.postProcessBeforeInitialization(instance, name);
            }
            // 初始化
            if (instance instanceof InitializingBean) {
                InitializingBean initObject = (InitializingBean) instance;
                initObject.afterPropertiesSet();
            }
            /*
             * 初始化后
             * fixme AOP扩展点
             */
            for (BeanPostProcessor postProcessor : postProcessorList) {
                instance = postProcessor.postProcessAfterInitialization(instance, name);
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
