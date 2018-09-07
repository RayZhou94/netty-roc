package remoting.handler;

import common.Message;
import common.Request;
import common.Response;
import common.Future;
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
    Future invoke(Channel channel, Message message) {
        log.info("server handler invoke a message {}", message);
        Request request = (Request) message;

        //拿到消息中的请求后，解析消息里的方法，进行真正的调用，并且返回结果
        Future future =  methodHandler.process(request);
        Object result = future.get();
        //将结果封装到DefaultResponse中，然后返回
        Response defaultResponse = new Response();
        defaultResponse.setUuid(message.getUuid());
        defaultResponse.setResponse(result);

        Response response = new Response();
        response.setUuid(defaultResponse.getUuid());
        response.setResponse(defaultResponse.getResponse());
        //返回调用结果给客户端
        channel.writeAndFlush(SerializationUtil.inCode(response));

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
        Request request = (Request) SerializationUtil.deCode(msg);
        log.info("server handler invoke a message {}", request);
    }

    @Override
    void channelReadComplete(ChannelHandlerContext ctx) {

    }
}
