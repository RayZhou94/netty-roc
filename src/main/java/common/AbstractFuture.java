package common;

import lombok.Data;

/**
 * Created by shallowdream on 2018/9/29.
 */
@Data
public abstract class AbstractFuture<T> implements Future<T> {

    protected volatile String messageId;

    protected volatile T response;
}
