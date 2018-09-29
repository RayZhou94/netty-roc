package proxy;

import common.Future;

/**
 * Created by shallowdream on 2018/8/4.
 */
public interface MethodHandler {

    Future process(Object request);

}
