package remoting.handler;

import common.Future;
import common.Message;
import common.Request;
import common.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import proxy.MethodHandler;
import utils.AddressUtil;

import java.net.InetSocketAddress;

/**
 * Created by shallowdream on 2018/8/4.
 */
@Slf4j
public class ServerInvocationHandler extends SimpleChannelInboundHandler<Request> {

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
//        Request request = (Request) message;
//
//        //拿到消息中的请求后，解析消息里的方法，进行真正的调用，并且返回结果
//        Future future =  methodHandler.process(request);
//        Object result = future.get();
//        //将结果封装到DefaultResponse中，然后返回
//        Response defaultResponse = new Response();
//        defaultResponse.setMessageId(message.getMessageId());
//        defaultResponse.setData(result);
//
//        Response response = new Response();
//        response.setMessageId(defaultResponse.getMessageId());
//        response.setData(defaultResponse.getData());
//        //返回调用结果给客户端
//        channel.writeAndFlush(SerializationUtil.inCode(response));

        return null;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info(AddressUtil.buildAddress(address) + " connected");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info(AddressUtil.buildAddress(address) + " disconnected");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("channelReadComplete");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        log.info("server handler invoke a message {}", request);

        Response response = new Response();
        response.setMessageId(request.getMessageId());
        ctx.writeAndFlush(response);
    }
}
