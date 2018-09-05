package proxy;

import common.Request;

/**
 * Created by shallowdream on 2018/8/4.
 */
public interface ProxyBuilder {

    Object builderProxy();

    /**
     * 解析request生成InvocationMethod
     * @param request
     * @return
     */
    InvocationMethod builderInvocationMethod(Request request);
}
