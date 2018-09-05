package protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by shallowdream on 2018/8/2.
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = -2007967678182445009L;

    private String code;

    private String message;

    private Object data;
}
