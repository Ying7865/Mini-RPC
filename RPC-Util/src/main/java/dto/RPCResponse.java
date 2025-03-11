package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class RPCResponse implements Serializable {

    //RPC call result.
    private Integer responseCode;
    private String responseMessage;

    // method return result
    private Class<?> dataType;
    private Object responseResult;

    public static RPCResponse success(Object object){
        return RPCResponse.builder().responseCode(200).responseMessage("Remote Process Success!!").responseResult(object).dataType(object.getClass()).build();
    }
    public static RPCResponse fail(){
        return RPCResponse.builder().responseCode(500).responseMessage("Remote Proccess Failure!!").build();
    }
}
