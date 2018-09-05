package remoting;

import common.Future;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import serialization.SerializationUtil;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shallowdream on 2018/8/4.
 */
@Slf4j
public class ClientInvocationHandler extends AbstractInvocationHandler {

    private volatile AtomicBoolean connected = new AtomicBoolean(false);

    private Channel channel;

    private DefaultFuture future ;

    @Override
    void init(Channel channel) {
        log.info("channel init... ");
        this.channel = channel;
        connected.set(true);
    }

    public boolean isConnected(){
        return connected.get();
    }

    public Channel getChannel(){
        return this.channel;
    }

    public Future sendMessage(Message message){
        future = new DefaultFuture();

        //发送消息，如果未连接，就进行忙等
        while (!isConnected()){
        }
        byte[] req = SerializationUtil.serialize(message);
        ByteBuf m = Unpooled.buffer(req.length);
        m.writeBytes(req);
        channel.writeAndFlush(m);
        //在这里直接设置结果Future，但是只有当服务端返回结果后，Future才有结果
        future.setChannel(channel);
        future.setCreateTime(System.currentTimeMillis());
        NettyFactory.registerCallbackMap(message.getUuid(), future);
        return future;
    }

    @Override
    Future invoke(Channel channel, Message message) {
        log.info("invocation result callback... {}", message);
        future.setChannel(channel);
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setUuid(message.getUuid());
        defaultResponse.setResponse(message.getData());
        future.setDefaultResponse(defaultResponse);
        return future;
    }
}
