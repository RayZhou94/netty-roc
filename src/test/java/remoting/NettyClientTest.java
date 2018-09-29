package remoting;

import common.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Created by shallowdream on 2018/9/7.
 */
@Slf4j
public class NettyClientTest {

    public static void main(String[] args) {
        Request request = new Request();
        request.setMessageId(UUID.randomUUID().toString());
        log.info("client send message {}", request);
        ResponseFuture<Response> future = (ResponseFuture<Response>) new NettyTransport().send(request);

        log.info("{}", future.get());

//        Message message1 = new Request();
//        message1.setMessageId(UUID.randomUUID().toString());
//        ResponseFuture future1 = (ResponseFuture) new NettyTransport().send(message1);
//        log.info("{}", future1.get());

    }


}