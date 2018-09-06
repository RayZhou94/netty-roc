package common;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

@Data
public class Request extends Message implements Serializable {

    private String serviceName;

    private String methodName;

    private Object[] args;

    private Class<?> parameterType;
}
