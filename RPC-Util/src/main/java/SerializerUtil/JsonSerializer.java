package SerializerUtil;

import DTO.RPCRequest;
import DTO.RPCResponse;
import com.alibaba.fastjson.JSONObject;

public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = JSONObject.toJSONBytes(obj);
        return bytes;
    }

    /**
     * Directly Transfer Json to RPC Message Template.
     * If Parameter is an object, JSON wouldn't recognize the specific Class and help you to mapping.
     * So We just need to recognize if the parameter is an object and map it to an object.
     * Need to implement this feature in both RPCRequest and RPC Response.
     *
     * @param bytes
     * @param messageType
     * @return
     */
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        System.out.println("Receive Message...");

        if (messageType == MessageType.Request.getCode()) {
            System.out.println("MessageType is Request");
            RPCRequest request = JSONObject.parseObject(bytes, RPCRequest.class);

            if (request.getRequestParams() == null) {
                return request;
            }

            Object[] objects = new Object[request.getRequestParams().length];

            for(int i = 0; i < objects.length; i++){
                Class<?> paramsType = request.getRequestParamsType()[i];
                System.out.println("[Serialize Process] Try to map \"" +paramsType +"\" to \"" + request.getRequestParams()[i].getClass() + "\"");
                if (!paramsType.isAssignableFrom(request.getRequestParams()[i].getClass())){
                    //If current class not match the specific class. Try to Parse it to specific class.
                    objects[i] = JSONObject.toJavaObject((JSONObject) request.getRequestParams()[i],request.getRequestParamsType()[i]);
                    System.out.println("[Serialize Process] Final class: " +objects[i].getClass().getSimpleName());

                }else{
                    objects[i] = request.getRequestParams()[i];
                }

            }
            request.setRequestParams(objects);
            obj = request;

        } else if (messageType == MessageType.Response.getCode()) {
            System.out.println("MessageType is Response");
            RPCResponse response = JSONObject.parseObject(bytes, RPCResponse.class);
            Class<?> dataType = response.getDataType();
            if (!dataType.isAssignableFrom(response.getResponseResult().getClass())) {
                response.setResponseResult(JSONObject.toJavaObject((JSONObject) response.getResponseResult(), dataType));
            }
            obj = response;
        } else {
            System.out.println("Unknown Message Type, please send a valid MessageType");
            throw new RuntimeException();
        }
        return obj;
    }

    @Override
    public int getType() {
        return 1;
    }
}
