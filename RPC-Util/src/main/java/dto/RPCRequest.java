package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class RPCRequest implements Serializable {
    //Server class
    private String requestInterface;
    private String requestMethod;
    private Object[] requestParams;
    private Class<?>[] requestParamsType;
}
