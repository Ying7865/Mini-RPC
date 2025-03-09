package SerializerUtil;

import DTO.RPCRequest;
import DTO.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RPCEncoder extends MessageToByteEncoder {
    private Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        if(object instanceof RPCRequest){
            byteBuf.writeShort(MessageType.Request.getCode());
        }
        else if(object instanceof RPCResponse){
            byteBuf.writeShort(MessageType.Response.getCode());
        }

        byteBuf.writeShort(serializer.getType());

        byte[] serialize = serializer.serialize(object);

        byteBuf.writeInt(serialize.length);

        byteBuf.writeBytes(serialize);

    }
}
