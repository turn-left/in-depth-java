package com.ethen.rpc.remote;

import com.ethen.rpc.remote.vo.MsgVO;

/**
 * 发送消息服务 => 服务的存根stub
 */
public interface SendSms {
    void sendMsg(MsgVO msg);
}
