package serialization;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import common.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by shallowdream on 2018/8/2.
 */
@Slf4j
public class SerializationUtil {

    public static byte[] serialize(Object obj) {
        if(obj==null) throw new NullPointerException();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        try {
            ho.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    public static Object deserialize(byte[] by){
        if(by==null) throw new NullPointerException();

        ByteArrayInputStream is = new ByteArrayInputStream(by);
        HessianInput hi = new HessianInput(is);
        try {
            return hi.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object deCode(Object msg){
        ByteBuf in = (ByteBuf) msg;
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        return deserialize(req);
    }

    public static byte[] inCode(Message message){
        byte[] bytes = serialize(message);
        ByteBuf m = Unpooled.buffer(bytes.length);
        m.writeBytes(bytes);
        return bytes;
    }
}
