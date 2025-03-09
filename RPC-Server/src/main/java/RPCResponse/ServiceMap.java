package RPCResponse;

import java.util.HashMap;
import java.util.Map;

public class ServiceMap {
    private Map<String, Object> interfaceMap;

    public ServiceMap(){
        this.interfaceMap = new HashMap<>();
    }

    public void setInterfaceMap(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println("[Inject Service] interfaceName: "+anInterface.getSimpleName()+"  serviceName"+service.getClass().getName());
            this.interfaceMap.put(anInterface.getSimpleName(), service);
        }

    }

    public Object getService(String interfaceName){
        return this.interfaceMap.get(interfaceName);
    }

}
