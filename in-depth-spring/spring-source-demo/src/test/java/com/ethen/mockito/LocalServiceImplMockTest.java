package com.ethen.mockito;

import com.ethen.data.model.Node;
import com.ethen.data.service.impl.LocalServiceImpl;
import com.ethen.data.service.impl.RemoteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

/**
 * mockito测试01
 * <p>
 * Mockito一般通过创建mock或spy对象，并制定具体返回规则来实现模拟的功能，在调用完成后还可以进行方法调用验证以检验程序逻辑是否正确。
 * <br>mock和spy对象的区别是mock对象对于未指定处理规则的调用会按方法返回值类型返回该类型的默认值。
 * <ul>mock对象返回默认值情况</ul>
 * <li>int -> 0</li>
 * <li>long -> 0</li>
 * <li>boolean -> false</li>
 * <li>Object -> null</li>
 * <li>void -> do nothing</li>
 * <ul>spy对象返回默认值情况</ul>
 * <li>spy对象在未指定处理规则时则会直接调用真实方法。</li>
 */
@RunWith(MockitoJUnitRunner.class) // 让测试运行与mockito环境
public class LocalServiceImplMockTest {
    /**
     * 注解<code>@InjectMocks</code>表示localService对象需要被注入mock对象
     */
    @InjectMocks
    private LocalServiceImpl localService;

    /**
     * 注解<code>@Mock</code>会自动创建一个mock对象并注入到<code>@InjectMocks</code>对象中
     */
    @Mock
    private RemoteServiceImpl remoteService;

    /**
     * private LocalServiceImpl localService2;
     * private RemoteServiceImpl remoteService2;
     * <p>
     * 如果不使用上述注解，则可以使用<code>@Before</code>方法来手动进行mock对象的创建和注入，但会多几行代码
     *
     * @Before public void setUp() {
     * localService2 = new LocalServiceImpl();
     * // 创建mock对象
     * remoteService2 = Mockito.mock(RemoteServiceImpl.class);
     * // 注入依赖对象
     * Whitebox.setInternalState(localService2, "remoteService", remoteService2);
     * }
     */

    @Test
    public void testMock() {
        // 创建一个Node对象作为返回值
        int mockNum = 1;
        String mockName = "target";
        Node target = new Node(mockNum, mockName);
        // 指定条件返回
        Mockito.when(remoteService.getRemoteNode(1)).thenReturn(target);
        // 调用业务方法，其内部调用依赖对象方法
        final Node result = localService.getRemoteNode(1);
        // 做一些断言
        Assert.assertEquals(target, result);
        Assert.assertEquals(mockName, result.getName());
        Assert.assertEquals(mockNum, result.getNum());

        // 未指定参数，返回默认初始值
        int notMock = 2;
        final Node result2 = localService.getRemoteNode(notMock);
        Assert.assertNull(result2);
    }

}
