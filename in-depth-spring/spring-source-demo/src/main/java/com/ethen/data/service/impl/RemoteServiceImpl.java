package com.ethen.data.service.impl;

import com.ethen.data.model.Node;
import com.ethen.data.service.RemoteService;
import org.springframework.stereotype.Service;

@Service
public class RemoteServiceImpl implements RemoteService {
    @Override
    public Node getRemoteNode(int num) {
        return new Node(num, "Node from remote service");
    }
}
