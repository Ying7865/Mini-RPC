package SerializerUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RPCDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        short messageType = byteBuf.readShort();
        // 现在还只支持request与response请求
        if(messageType != MessageType.Request.getCode() &&
                messageType != MessageType.Response.getCode()){
            System.out.println("Unsupported Message Type, please retry");
            return;
        }

        short serializerType = byteBuf.readShort();

        Serializer serializer = Serializer.getSerializerByCode(serializerType);
        if(serializer == null){
            throw new RuntimeException("Unsupported Serializer Type, please retry");
        }

        int length = byteBuf.readInt();

        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Object deserialize = serializer.deserialize(data, messageType);
        list.add(deserialize);

    }
}
