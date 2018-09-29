package common;


import lombok.Data;
/**
 * Created by shallowdream on 2018/8/3.
 */

@Data
public class ResponseFuture<T> extends AbstractFuture<T> {

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
