package DTO;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RPCRequest implements Serializable {
    //Server class
    private String requestInterface;
    private String requestMethod;
    private Object[] requestParams;
    private Class<?>[] requestParamsType;
}
