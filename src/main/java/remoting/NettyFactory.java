package remoting;


import common.Future;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shallowdream on 2018/8/3.
 */
@Data
@Slf4j
public class NettyFactory {

    private static final Map<String, Future> callbackMap = new ConcurrentHashMap<>();

    public static Future registerCallbackMap(String connectionId, Future future){
        log.info("register one connection, connectionId = {}", connectionId);
        return callbackMap.put(connectionId, future);
    }

    public static Future unRegisterCallbackMap(String connectionId){
        log.info("unRegister one connection, connectionId = {}", connectionId);
        return callbackMap.remove(connectionId);
    }

}
