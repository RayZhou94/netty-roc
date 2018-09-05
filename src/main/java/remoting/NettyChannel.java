package remoting;

import io.netty.channel.Channel;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shallowdream on 2018/8/3.
 */
@Data
@Deprecated
public class NettyChannel {

    private static Map<Channel, NettyChannel> nettyChannelMap = new ConcurrentHashMap<>();

    private Channel channel;

    private DefaultResponse response;

    public NettyChannel(Channel channel){
        this.channel = channel;
        nettyChannelMap.put(channel, this);
    }

    public Channel getChannel(){
        return channel;
    }

    public static NettyChannel nettyChannel(Channel channel){
        return nettyChannelMap.get(channel);
    }

}
