package RPCResponse;

import RegisterUtil.ServiceRegister;
import RegisterUtil.ZooKeeperServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ServiceMap {
    private Map<String, Object> interfaceMap;
    private ServiceRegister serviceRegister;

    public ServiceMap(){
        this.interfaceMap = new HashMap<>();
        this.serviceRegister = new ZooKeeperServiceRegister();
    }

    public void setInterfaceMap(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println("[Inject Service] interfaceName: "+anInterface.getSimpleName()+"  serviceName"+service.getClass().getName());
            this.interfaceMap.put(anInterface.getSimpleName(), service);

        }
    }

    public void registerService(String host, int port){
        for (Map.Entry<String, Object> service : interfaceMap.entrySet()) {
            serviceRegister.register(service.getKey(),new InetSocketAddress(host,port));
            System.out.println("[Register Service] : host: "+host+"  port: "+port); ;
        }

    }

    public Object getService(String interfaceName){
        return this.interfaceMap.get(interfaceName);
    }

}
