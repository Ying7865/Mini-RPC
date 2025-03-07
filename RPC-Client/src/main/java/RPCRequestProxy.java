import DTO.RPCRequest;
import DTO.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class RPCRequestProxy implements InvocationHandler {
    // Should be included the target service address.
    private String host;
    private int port;

    <T> T getProxy(Class<T> clazz){
        Object object = Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},this);
        return (T)object;
    }

    //JDK Dynamic Proxy, Enhance the target class by proxy.
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder()
                .requestInterface(method.getDeclaringClass().getName())
                .requestMethod(method.getName())
                .requestParamsType(method.getParameterTypes())
                .requestParams(args).build();
        RPCResponse response = CommunicationCenter.sendRequest(host,port,request);
        return response.getResponseResult();
    }
}
