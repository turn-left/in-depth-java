package com.ethen.bean;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public void send() {
        System.err.println("@MessageService send()==============");
    }
}
