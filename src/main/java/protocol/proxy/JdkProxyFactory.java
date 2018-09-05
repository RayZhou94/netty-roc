package protocol.proxy;

import protocol.invoke.Invoker;

import java.lang.reflect.Proxy;

/**
 * Created by shallowdream on 2018/8/2.
 */
public class JdkProxyFactory implements ProxyFactory{

    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new InvokerInvocationHandler(invoker));
    }
}
