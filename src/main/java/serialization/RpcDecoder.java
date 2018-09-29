package serialization;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by shallowdream on 2018/8/2.
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {



    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
//        in.markReaderIndex();
//        int dataLength = in.readInt();
//        if (in.readableBytes() < dataLength) {
//            in.resetReaderIndex();
//            return;
//        }
//        byte[] data = new byte[dataLength];
//        in.readBytes(data);
//
//        Object obj = SerializationUtil.deserialize(data);
        out.add(in.readInt());
    }

}
