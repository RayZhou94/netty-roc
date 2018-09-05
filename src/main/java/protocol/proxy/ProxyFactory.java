package protocol.proxy;

import protocol.invoke.Invoker;

/**
 * Created by shallowdream on 2018/8/2.
 */
public interface ProxyFactory {

    <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces);
}
