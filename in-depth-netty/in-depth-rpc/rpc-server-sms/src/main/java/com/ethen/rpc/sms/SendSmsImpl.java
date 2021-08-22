package com.ethen.rpc.sms;

import com.ethen.rpc.remote.SendSms;
import com.ethen.rpc.remote.vo.MsgVO;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * FPC服务接口实现
 */
public class SendSmsImpl implements SendSms {
    @SneakyThrows
    @Override
    public void sendMsg(MsgVO msg) {
        System.out.println("*** start sendMsg ***");
        TimeUnit.MILLISECONDS.sleep(100);
        System.err.println("=> 发送信息[" + msg.getContent() + "]给[" + msg.getTarget() + "]");
        System.out.println("*** end sendMsg ***");
    }
}
