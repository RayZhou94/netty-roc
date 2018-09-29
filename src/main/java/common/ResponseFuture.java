package common;


import io.netty.channel.Channel;
import lombok.Data;
/**
 * Created by shallowdream on 2018/8/3.
 */

@Data
public class ResponseFuture<T> implements Future<T> {

    private volatile String messageId;

    private volatile T response;

    public ResponseFuture(){

    }

    public ResponseFuture(Message message) {
        this.messageId = message.getMessageId();
    }

    @Override
    public T get(){
        while (response == null){
        }
        return response;
    }

    @Override
    public boolean isDone() {
        while (response == null) {
        }
        return true;
    }

}
