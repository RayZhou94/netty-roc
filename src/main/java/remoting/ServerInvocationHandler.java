package remoting;

import common.Response;
import common.Future;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import proxy.MethodHandler;
import serialization.SerializationUtil;

/**
 * Created by shallowdream on 2018/8/4.
 */
@Slf4j
public class ServerInvocationHandler extends AbstractInvocationHandler {

    private MethodHandler methodHandler;


    public void setMethodHandler(MethodHandler methodHandler){
        this.methodHandler = methodHandler;
    }

    /**
     * 服务器处理器处理接收到的消息
     * @param channel
     * @param message
     * @return
     */
    Future invoke(Channel channel, Request message) {
        log.info("server handler invoke a message {}", message);
        common.Request request = (common.Request) message.getData();

        //拿到消息中的请求后，解析消息里的方法，进行真正的调用，并且返回结果
        Future future =  methodHandler.process(request);
        Object result = future.get();
        //将结果封装到DefaultResponse中，然后返回
        Response defaultResponse = new Response();
        defaultResponse.setUuid(message.getUuid());
        defaultResponse.setResponse(result);

        Request response = new Request(defaultResponse.getUuid(), defaultResponse.getResponse());
        //返回调用结果给客户端
        byte[] req = SerializationUtil.serialize(response);
        ByteBuf m = Unpooled.buffer(req.length);
        m.writeBytes(req);
        channel.writeAndFlush(m);

        return future;
    }

    @Override
    void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    void channelInactive(ChannelHandlerContext ctx) {

    }

    @Override
    void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("server handler invoke a message {}", msg);
//        ByteBuf m = Unpooled.buffer(msg);
//        Request request = SerializationUtil.deserialize()
    }
}
