package com.ethen.security;

/**
 * java安全管理机制初探
 * <p>
 * {@link - https://developer.aliyun.com/article/57223} Java安全——安全管理器、访问控制器和类装载器
 * {@link - https://www.zybuluo.com/changedi/note/237999#a} Java安全——理解Java沙箱
 * <p>
 */
public class UseSecurityMgr {
    public static void main(String[] args) {
        final MySM mySM = new MySM();
        System.out.println(System.getSecurityManager());
//        System.setSecurityManager(mySM);
        System.exit(0);
    }


    static class MySM extends SecurityManager {
        @Override
        public void checkExit(int status) {
            throw new SecurityException("no exit");
        }
    }
}
