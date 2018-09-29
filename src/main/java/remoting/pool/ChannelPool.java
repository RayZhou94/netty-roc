package remoting.pool;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shallowdream on 2018/9/29.
 */
public class ChannelPool {

    /**
     * key为address
     */
    private static Map<String, Channel> serverChannelMap = new ConcurrentHashMap<>();

    private static Map<String, Channel> clientChannelMap = new ConcurrentHashMap<>();

    public static void putServerChannel(String address, Channel channel){
        serverChannelMap.put(address, channel);
    }

    public static Channel getServerChannel(String address){
        return serverChannelMap.get(address);
    }

    public static void removeServerChannel(String address){
        serverChannelMap.remove(address);
    }

    public static void putClientChannel(String address, Channel channel){
        clientChannelMap.put(address, channel);
    }

    public static Channel getClientChannel(String address){
        return clientChannelMap.get(address);
    }

    public static void removeClientChannel(String address){
        clientChannelMap.remove(address);
    }


}
