package com.ethen.agent;

import java.lang.instrument.Instrumentation;

public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("=========== Hello, java agent, agentArgs=" + agentArgs + " ==========");
    }
}
