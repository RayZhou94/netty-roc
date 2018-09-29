package remoting.pool;

import common.AbstractFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shallowdream on 2018/9/29.
 */
public class FuturePool {

    private static Map<String, AbstractFuture> futureMap = new ConcurrentHashMap<>();

    public static void put(String messageId, AbstractFuture future){
        futureMap.put(messageId, future);
    }

    public static AbstractFuture get(String messageId){
        return futureMap.get(messageId);
    }
}
