package com.ethen.data.service.impl;

import com.ethen.data.model.Node;
import com.ethen.data.service.LocalService;
import com.ethen.data.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalServiceImpl implements LocalService {

    @Autowired
    private RemoteService remoteService;

    @Override
    public Node getRemoteNode(int num) {
        return remoteService.getRemoteNode(num);
    }
}
