package remoting;

import common.DefaultFuture;
import common.Response;
import common.Future;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
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


    public boolean isConnected(){
        return connected.get();
    }

    public Channel getChannel(){
        return this.channel;
    }

    public Future sendMessage(Request request){
        future = new DefaultFuture();

        //发送消息，如果未连接，就进行忙等
        while (!isConnected()){
        }
        byte[] req = SerializationUtil.serialize(request);
        ByteBuf m = Unpooled.buffer(req.length);
        m.writeBytes(req);
        channel.writeAndFlush(m);
        //在这里直接设置结果Future，但是只有当服务端返回结果后，Future才有结果
        future.setChannel(channel);
        future.setCreateTime(System.currentTimeMillis());
        NettyFactory.registerCallbackMap(request.getUuid(), future);
        return future;
    }

    @Override
    void channelActive(ChannelHandlerContext ctx) {
        this.channel  = ctx.channel();
        this.connected.set(true);
    }

    @Override
    void channelInactive(ChannelHandlerContext ctx) {
        this.connected.set(false);
    }

    @Override
    void channelRead(ChannelHandlerContext ctx, Object msg) {

    }
}
