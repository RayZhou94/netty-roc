package remoting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by shallowdream on 2018/8/3.
 */
@Data
@AllArgsConstructor
@ToString
public class Message implements Serializable {
    private static final long serialVersionUID = -535972098007650942L;

    private String uuid;

    private Object data;
}
