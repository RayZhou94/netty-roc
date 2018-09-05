package proxy;

import common.Future;
import common.Request;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by shallowdream on 2018/8/4.
 */
@Slf4j
public class DefaultMethodHandler implements MethodHandler {

    private ProxyBuilder proxyBuilder;

    public void setProxyBuilder(ProxyBuilder proxyBuilder){
        this.proxyBuilder = proxyBuilder;
    }

    @Override
    public Future process(Object request) {
        //先通过参数拿到方法处理器
        InvocationMethod invocationMethod = proxyBuilder.builderInvocationMethod((Request) request);
        Method method = invocationMethod.getMethod();
        Object proxy = invocationMethod.getProxy();
        Object[] args = invocationMethod.getArgs();
        //设置允许方法反射调用
        method.setAccessible(true);
        Object response = null;
        try {
            //方法反射调用，并获取返回信息
            response = method.invoke(proxy, args);
        } catch (IllegalAccessException e) {
            log.error("reflect method invoke error {}", e.getMessage());
        } catch (InvocationTargetException e) {
            log.error("reflect method invoke error {}", e.getMessage());
        }
        //处理完方法调用，然后放到MethodInvokeFuture中返回
        MethodInvokeFuture methodInvokeFuture = new MethodInvokeFuture();
        methodInvokeFuture.setResponse(response);
        return methodInvokeFuture;
    }
}
