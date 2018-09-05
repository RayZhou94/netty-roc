package proxy;

import common.Future;
import lombok.Data;

/**
 * Created by shallowdream on 2018/8/5.
 */
public class MethodInvokeFuture implements Future {

    private Object response;

    public void setResponse(Object response){
        this.response = response;
    }

    @Override
    public Object get() {
        return response;
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
