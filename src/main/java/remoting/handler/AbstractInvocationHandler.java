package remoting.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by shallowdream on 2018/8/3.
 */
public abstract class AbstractInvocationHandler {

    /**
     * 服务器连接时触发
     * @param ctx
     */
    abstract void channelActive(ChannelHandlerContext ctx);

    /**
     * 服务器断连时触发
     * @param ctx
     */
    abstract void channelInactive(ChannelHandlerContext ctx);

    /**
     * 服务器接收到数据时触发
     * @param ctx
     * @param msg
     */
    abstract void channelRead(ChannelHandlerContext ctx, Object msg);

    /**
     * 服务器处理完数据时触发
     * @param ctx
     */
    abstract void channelReadComplete(ChannelHandlerContext ctx);
}
