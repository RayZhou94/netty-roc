package proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by shallowdream on 2018/8/3.
 */
public class JdkProxy implements InvocationHandler {

    public JdkProxy(){

    }

    public Object getProxy(Class<?>[] interfaces){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
