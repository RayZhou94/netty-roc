package remoting.handler;

import common.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import remoting.NettyFactory;
import serialization.SerializationUtil;

/**
 * Created by shallowdream on 2018/8/4.
 */
@Slf4j
public class ClientInvocationHandler extends AbstractInvocationHandler {


    private Channel channel;

    private DefaultFuture future ;


    public Future sendMessage(Message message){
        Request request = (Request) message;

        future = new DefaultFuture();
        //发送消息，如果未连接，就进行忙等
        byte[] bytes = SerializationUtil.inCode(request);
        channel.writeAndFlush(bytes);
        //在这里直接设置结果Future，但是只有当服务端返回结果后，Future才有结果
        future.setChannel(channel);
        future.setCreateTime(System.currentTimeMillis());
        NettyFactory.registerCallbackMap(request.getUuid(), future);
        return future;
    }

    @Override
    void channelActive(ChannelHandlerContext ctx) {
        this.channel  = ctx.channel();
    }

    @Override
    void channelInactive(ChannelHandlerContext ctx) {
    }

    @Override
    void channelRead(ChannelHandlerContext ctx, Object msg) {
        Response response = (Response) SerializationUtil.deCode(msg);
        log.info("client receiver a message {}", response);
    }

    @Override
    void channelReadComplete(ChannelHandlerContext ctx) {

    }
}
