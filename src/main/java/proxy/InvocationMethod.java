package proxy;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * Created by shallowdream on 2018/8/4.
 * 这里是代理方法调用的基本信息
 */
@Data
public class InvocationMethod {

    /**
     * 代理方法
     */
    private Method method;

    /**
     * 参数
     */
    private Object[] args;

    /**
     * 对象
     */
    private Object proxy;
}
