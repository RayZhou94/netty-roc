package remoting;

import common.Future;
import io.netty.channel.Channel;

/**
 * Created by shallowdream on 2018/8/3.
 */
public abstract class AbstractInvocationHandler {

    abstract void init(Channel channel);

    abstract Future invoke(Channel channel, Message message);
}
