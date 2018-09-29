package remoting.handler;

import common.Request;
import common.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import proxy.MethodHandler;
import remoting.pool.ChannelPool;
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

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info(AddressUtil.buildAddress(address) + " connected");
        ChannelPool.putClientChannel(AddressUtil.buildAddress(address), ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info(AddressUtil.buildAddress(address) + " disconnected");
        ChannelPool.removeClientChannel(AddressUtil.buildAddress(address));
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
