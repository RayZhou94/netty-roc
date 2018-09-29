package serialization;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shallowdream on 2018/8/2.
 */
@Slf4j

public class SerializationUtil {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    private SerializationUtil() {
    }

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
//
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
//
//    public static ByteBuf inCode(Message message){
//        byte[] bytes = serialize(message);
//        ByteBuf m = Unpooled.buffer(bytes.length);
//        m.writeBytes(bytes);
//        return m;
//    }

//    /**
//     * 序列化（对象 -> 字节数组）
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> byte[] serialize(T obj) {
//        Class<T> cls = (Class<T>) obj.getClass();
//        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//        try {
//            Schema<T> schema = getSchema(cls);
//            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        } finally {
//            buffer.clear();
//        }
//    }

//    /**
//     * 反序列化（字节数组 -> 对象）
//     */
//    public static <T> T deserialize(byte[] data, Class<T> cls) {
//        try {
//            T message = (T) objenesis.newInstance(cls);
//            Schema<T> schema = getSchema(cls);
//            ProtostuffIOUtil.mergeFrom(data, message, schema);
//            return message;
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        }
//    }
//
//
//    @SuppressWarnings("unchecked")
//    private static <T> Schema<T> getSchema(Class<T> cls) {
//        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
//        if (schema == null) {
//            schema = RuntimeSchema.createFrom(cls);
//            if (schema != null) {
//                cachedSchema.put(cls, schema);
//            }
//        }
//        return schema;
//    }
}
