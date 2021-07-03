package com.ethen.chap01.task;

/**
 * description: 虚拟机栈 字节码指定执行原理
 * java字节码指令集{@link -> https://zhuanlan.zhihu.com/p/352723419}
 *
 * @author ethenyang@126.com
 * @since 2021/07/01
 */
public class ConstantPool {
    public static void main(String[] args) {
        String b = "享学";
        String a = b + "课堂";
        System.err.println(a.intern() == a);
    }
}
//Code:
//        stack=3, locals=3, args_size=1
//        0: ldc           #2                  // String 享学
//        2: astore_1
//        3: new           #3                  // class java/lang/StringBuilder
//        6: dup
//        7: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
//        10: aload_1
//        11: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//        14: ldc           #6                  // String 课堂
//        16: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//        19: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
//        22: astore_2
//        23: getstatic     #8                  // Field java/lang/System.err:Ljava/io/PrintStream;
//        26: aload_2
//        27: invokevirtual #9                  // Method java/lang/String.intern:()Ljava/lang/String;
//        30: aload_2
//        31: if_acmpne     38
//        34: iconst_1
//        35: goto          39
//        38: iconst_0
//        39: invokevirtual #10                 // Method java/io/PrintStream.println:(Z)V
//        42: return
//        LineNumberTable:
//        line 11: 0
//        line 12: 3
//        line 13: 23
//        line 14: 42
//        LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//        0      43     0  args   [Ljava/lang/String;
//        3      40     1     b   Ljava/lang/String;
//        23      20     2     a   Ljava/lang/String;