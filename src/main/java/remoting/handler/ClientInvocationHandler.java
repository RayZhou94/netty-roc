package remoting.handler;

import common.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import remoting.pool.ChannelPool;
import remoting.pool.FuturePool;
import utils.AddressUtil;

import java.net.InetSocketAddress;

/**
 * Created by shallowdream on 2018/8/4.
 */
@Slf4j
public class ClientInvocationHandler extends SimpleChannelInboundHandler<Response> {

    public Future<Response> sendMessage(Request request){
        //发送消息，如果未连接，就进行忙等
        Channel channel = ChannelPool.getServerChannel(request.getTargetAddress());
        while (channel == null){
            channel = ChannelPool.getServerChannel(request.getTargetAddress());
        }

        channel.writeAndFlush(request);
        AbstractFuture future = new ResponseFuture();
        future.setMessageId(request.getMessageId());
        FuturePool.put(request.getMessageId(), future);
        return future;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        ChannelPool.putServerChannel(AddressUtil.buildAddress(address), ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        ChannelPool.removeServerChannel(AddressUtil.buildAddress(address));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        log.info("client receiver a response {}", response);
        AbstractFuture future = FuturePool.get(response.getMessageId());
        future.setResponse(response);
    }
}
