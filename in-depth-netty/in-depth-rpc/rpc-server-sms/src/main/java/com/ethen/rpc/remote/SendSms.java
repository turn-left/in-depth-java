package com.ethen.rpc.remote;

import com.ethen.rpc.base.RpcService;
import com.ethen.rpc.remote.vo.MsgVO;

/**
 * 发送消息服务 => 服务的存根stub
 */
@RpcService
public interface SendSms {
    void sendMsg(MsgVO msg);
}
