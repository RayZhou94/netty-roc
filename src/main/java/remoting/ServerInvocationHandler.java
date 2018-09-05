package remoting;

import common.Future;
import common.Request;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import proxy.MethodHandler;
import serialization.SerializationUtil;

/**
 * Created by shallowdream on 2018/8/4.
 */
@Slf4j
public class ServerInvocationHandler extends AbstractInvocationHandler {

    private MethodHandler methodHandler;

    @Override
    void init(Channel channel) {

    }

    public void setMethodHandler(MethodHandler methodHandler){
        this.methodHandler = methodHandler;
    }

    /**
     * 服务器处理器处理接收到的消息
     * @param channel
     * @param message
     * @return
     */
    @Override
    Future invoke(Channel channel, Message message) {
        log.info("server handler invoke a message {}", message);
        Request request = (Request) message.getData();

        //拿到消息中的请求后，解析消息里的方法，进行真正的调用，并且返回结果
        Future future =  methodHandler.process(request);
        Object result = future.get();
        //将结果封装到DefaultResponse中，然后返回
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setUuid(message.getUuid());
        defaultResponse.setResponse(result);

        Message response = new Message(defaultResponse.getUuid(), defaultResponse.getResponse());
        //返回调用结果给客户端
        byte[] req = SerializationUtil.serialize(response);
        ByteBuf m = Unpooled.buffer(req.length);
        m.writeBytes(req);
        channel.writeAndFlush(m);

        return future;
    }
}
