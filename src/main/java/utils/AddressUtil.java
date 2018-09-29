package utils;

import java.net.InetSocketAddress;

/**
 * Created by shallowdream on 2018/9/7.
 */
public class AddressUtil {

    public static String buildAddress(InetSocketAddress address){
        return address.getHostString() + ":" + address.getPort();
    }
}
