package remoting;


import common.Future;
import remoting.handler.ClientInvocationHandler;
import threadpool.RpcThreadPool;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * Created by shallowdream on 2018/8/2.
 */
public class NettyTransport {

    static ClientInvocationHandler clientInvocationHandler = new ClientInvocationHandler();
    static NettyClient nettyClient = new NettyClient("localhost", 8001, clientInvocationHandler);

    public Future send(Object msg){
        Request request = new Request(UUID.randomUUID().toString(), msg);
        ExecutorService executorService = RpcThreadPool.executorService();
        executorService.execute(()->{
            nettyClient.connect();
        });
        return clientInvocationHandler.sendMessage(request);
    }

}
