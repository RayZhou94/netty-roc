package protocol.invoke;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by shallowdream on 2018/8/2.
 */
@Data
public class Invocation implements Serializable {

    private static final long serialVersionUID = -2295356218074730373L;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    private Map<String, String> attachments;

    private transient Invoker<?> invoker;
}
