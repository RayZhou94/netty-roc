package common;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by shallowdream on 2018/9/6.
 */
@Data
@ToString
public abstract class Message implements Serializable {

    private static final long serialVersionUID = 3013625686674825639L;

    private String messageId;

}
