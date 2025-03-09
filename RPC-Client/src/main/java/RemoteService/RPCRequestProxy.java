package RemoteService;

import DTO.RPCRequest;
import DTO.RPCResponse;
import lombok.AllArgsConstructor;
import RPCRequest.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class RPCRequestProxy implements InvocationHandler {
    private RPCClient client;

    public <T> T getProxy(Class<T> clazz){
        Object object = Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},this);
        return (T)object;
    }

    //JDK Dynamic Proxy, Enhance the target class by proxy.
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder()
                .requestInterface(method.getDeclaringClass().getSimpleName())
                .requestMethod(method.getName())
                .requestParamsType(method.getParameterTypes())
                .requestParams(args).build();
        System.out.println("[Client] Request: " + request);
        RPCResponse response = client.sendRequest(request);
        return response.getResponseResult();
    }
}
