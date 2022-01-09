package com.ethen.mockito;

import com.ethen.data.model.Node;
import com.ethen.data.service.impl.LocalServiceImpl;
import com.ethen.data.service.impl.RemoteServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * mockito测试02<br>
 * spy外部依赖对象，并注入到业务对象中进行mock测试
 */
@RunWith(MockitoJUnitRunner.class)
public class LocalServiceImplSpyTest {
    /**
     * 需要业务测试的对象，其中有依赖需要被Mock注入
     */
    @InjectMocks
    private LocalServiceImpl localService;

    /**
     * 注意这里是@Spy注解
     */
    @Spy
    private RemoteServiceImpl remoteService;

    @Test
    public void testSpy() {
        int spyNum = 1;
        String spyName = "spyTarget";
        final Node spyNode = new Node(spyNum, spyName);
        // 给定条件返回指定结果数据
        Mockito.when(remoteService.getRemoteNode(1)).thenReturn(spyNode);
        // 调用业务方法
        final Node result = localService.getRemoteNode(1);
        // 断言
        Assert.assertEquals(spyNode, result);
        Assert.assertEquals(spyNum, result.getNum());
        Assert.assertEquals(spyName, result.getName());

        // 没有指定返回值时 调用真实方法
        final Node spyResult = localService.getRemoteNode(3);
        Assert.assertEquals(3, spyResult.getNum());
    }
}
