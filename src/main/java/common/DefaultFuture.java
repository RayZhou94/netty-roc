package common;


import common.Response;
import common.Future;
import io.netty.channel.Channel;
import lombok.Data;
import remoting.NettyFactory;

/**
 * Created by shallowdream on 2018/8/3.
 */

@Data
public class DefaultFuture implements Future {

    private volatile Response response;

    private Long createTime;

    private Long timeOut;

    private Channel channel;

    public DefaultFuture() {

    }

    @Override
    public Object get(){
        if (isDone()){
            //拿到数据后，就删除容器中的数据
            NettyFactory.unRegisterCallbackMap(response.getUuid());
            return response;
        }
        return null;
    }

    @Override
    public boolean isDone() {
        while (response == null) {
        }
        return true;
    }

}
