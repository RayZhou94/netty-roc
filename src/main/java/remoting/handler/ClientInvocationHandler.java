package remoting.handler;

import common.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import utils.AddressUtil;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shallowdream on 2018/8/4.
 */
@Slf4j
public class ClientInvocationHandler extends SimpleChannelInboundHandler<Response> {

    private volatile Channel channel;

    public Channel getChannel(){
        return channel;
    }

    private Map<String, Future> futureMap = new ConcurrentHashMap<>();


    public Future<Response> sendMessage(Request request){
        ResponseFuture<Response> future = new ResponseFuture<>(request);
        futureMap.put(request.getMessageId(), future);

        //发送消息，如果未连接，就进行忙等
        while (channel == null){
        }
        channel.writeAndFlush(request);
        return future;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info(AddressUtil.buildAddress(address) + " connected");
        this.channel  = ctx.channel();
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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        ResponseFuture future = (ResponseFuture) futureMap.get(response.getMessageId());
        future.setResponse(response);
        log.info("client receiver a response {}", response);
    }
}
