package protocol.invoke;

import protocol.Result;

/**
 * Created by shallowdream on 2018/8/2.
 */
public interface Invoker<T> {

    Class<T> getInterface();

    Result invoke(Invocation invocation);

}
