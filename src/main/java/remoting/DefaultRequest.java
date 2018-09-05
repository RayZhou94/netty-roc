package remoting;

import common.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by shallowdream on 2018/8/3.
 */
@Data
@AllArgsConstructor
@Deprecated
public class DefaultRequest implements Serializable {

    private String uuid;

    private Object request;
}
