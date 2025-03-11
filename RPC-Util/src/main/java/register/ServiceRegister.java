package register;


import java.net.InetSocketAddress;

/**
 * Service Register interface, to define the basic 2 feature for  register:
 * 1. Keep the Service name and address.
 * 2. Find the related accessible address by service name.
 */
public interface ServiceRegister {
    void register(String serviceName, InetSocketAddress serviceAddress);

    InetSocketAddress serviceDiscovery(String serviceName);

}
