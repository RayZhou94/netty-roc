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
       NettyClient nettyClient = new NettyClient("127.0.0.1", 8001, new ClientInvocationHandler());
       RpcThreadPool.executorService().submit(()-> {
           nettyClient.connect();
       });
       try {
           Thread.sleep(1000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       request.setTargetAddress("127.0.0.1" + ":" + 8001);
       return nettyClient.sendMessage(request);
   }

   public void bind(){
       new NettyServer(8001);
   }

}
