package remoting;


import common.Future;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * Created by shallowdream on 2018/8/3.
 */

@Data
public class DefaultFuture implements Future {

    private volatile DefaultResponse defaultResponse;

    private Long createTime;

    private Long timeOut;

    private Channel channel;

    public DefaultFuture() {

    }

    @Override
    public Object get(){
        if (isDone()){
            //拿到数据后，就删除容器中的数据
            NettyFactory.unRegisterCallbackMap(defaultResponse.getUuid());
            return defaultResponse;
        }
        return null;
    }

    @Override
    public boolean isDone() {
        while (defaultResponse == null) {
        }
        return true;
    }

}
