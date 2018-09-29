package serialization;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by shallowdream on 2018/8/2.
 */
@Slf4j
public class RpcEncoder extends MessageToByteEncoder {

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
//        byte[] data = SerializationUtil.serialize(in);
//        out.writeInt(data.length);
//        out.writeBytes(data);
        byte[] data = SerializationUtil.serialize(in);
        out.writeBytes(data);
    }
}
