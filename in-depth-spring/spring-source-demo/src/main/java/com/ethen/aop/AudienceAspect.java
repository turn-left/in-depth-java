package com.ethen.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 切面逻辑
 */
@Aspect
public class AudienceAspect {
    /**
     * 拦截com.ethen包下所有类的任意返回值的方法
     */
    @Pointcut("execution(** com.ethen.aop.Performance.perform(..))")
    public void performance() {
    }

    /**
     * 前置织入
     */
    @Before("performance()")
    public void takeSeats() {
        System.err.println("Audience takeSeats() ...");
    }

    /**
     * 后置织入
     */
    @AfterReturning("performance()")
    public void applause() {
        System.err.println("Audience applause() !!!");
    }

    /**
     * 统计方法耗时
     * com.ethen包及其子包下所有所有标注了@Cost注解的方法都会被织入切面
     *
     */
    @Around("execution(* com.ethen..*.*(..)) && @annotation(com.ethen.aop.annotation.Cost)")
    public Object costTime(ProceedingJoinPoint jp) {
        long start = System.currentTimeMillis();
        try {
            return jp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        } finally {
            long cost = System.currentTimeMillis() - start;
            System.err.println("complete invoke " + jp.toLongString() + "cost " + cost + " millis");
        }
    }
}
