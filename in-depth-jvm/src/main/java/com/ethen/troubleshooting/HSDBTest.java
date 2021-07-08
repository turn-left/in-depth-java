package com.ethen.troubleshooting;

/**
 * description:利用HSDB这个工具来分析对象在JVM中的情况
 * {@link -> https://blog.csdn.net/maosijunzi/article/details/45889965}
 * <p>
 * %JAVA_HOME%/lib 命令行执行 <code>java -cp sa-jdi.jar sun.jvm.hotspot.HSDB</code>
 *
 * @author ethenyang@126.com
 * @since 2021/07/06
 */
public class HSDBTest {
    static class Person {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static void main(String[] args) {
        final Person person = new Person();
        person.setName("黄飞鸿");
        person.setAge(45);
        System.err.println(person);
    }
}
