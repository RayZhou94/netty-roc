package remoting;


import common.Future;
import common.Message;
import common.Request;
import remoting.handler.ClientInvocationHandler;
import threadpool.RpcThreadPool;

/**
 * Created by shallowdream on 2018/8/2.
 */
public class NettyTransport {

   public Future send(Request request){
       ClientInvocationHandler handler = new ClientInvocationHandler();
       NettyClient nettyClient = new NettyClient("localhost", 8001, handler);
       RpcThreadPool.executorService().submit(()-> {
           nettyClient.connect();
       });
       return handler.sendMessage(request);
   }

   public void bind(){
       new NettyServer(8001);
   }

}
