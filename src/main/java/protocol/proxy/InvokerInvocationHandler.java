package protocol.proxy;

import protocol.invoke.Invocation;
import protocol.invoke.Invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by shallowdream on 2018/8/2.
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private final Invoker<?> invoker;

    public InvokerInvocationHandler(Invoker<?> invoker){
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new Invocation();
        invocation.setInvoker(invoker);
        invocation.setMethodName(method.getName());
        invocation.setArguments(args);
        invocation.setParameterTypes(method.getParameterTypes());
        return invoker.invoke(invocation);
    }
}
