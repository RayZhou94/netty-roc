package common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Arrays;

@Data
@ToString
public class Request extends Message implements Serializable {

    private static final long serialVersionUID = -4105873632549731903L;

    private String serviceName;

    private String methodName;

    private Object[] args;

    private Class<?> parameterType;
}
