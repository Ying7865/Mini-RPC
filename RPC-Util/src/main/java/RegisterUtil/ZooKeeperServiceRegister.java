package RegisterUtil;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

public class ZooKeeperServiceRegister implements ServiceRegister{
    // curator Client for register feature
    private CuratorFramework register;

    private static final String ROOT_PATH = "MiniRPC";

    //ZookeeperRegister initialize function. initial the connection when class creating.
    //The basic Zookeeper create process!.
    public ZooKeeperServiceRegister(){
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);

        this.register = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();
        this.register.start();
        System.out.println("Zookeeper launch successfully!!!");
    }

    @Override
    public void register(String serviceName, InetSocketAddress serviceAddress) {
        try {
            if (register.checkExists().forPath("/" + serviceName) == null){ // No target service exists, create a one. And service Name should not be deleted.
                register.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/"+serviceName);
            }
            // Template: UserService/127.0.0.1:8808
            String servicePath = "/" + serviceName + "/" + serviceAddress.getHostName() + serviceAddress.getPort();
            // Due to the service address is dynamic, so we wouldn't create it as an eternal node
            register.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(servicePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            List<String> children = register.getChildren().forPath("/" + serviceName);
            String children1 = children.get(0); //get first children.
            String[] splitIP = children1.split(":");
            return new InetSocketAddress(splitIP[0],Integer.parseInt(splitIP[1]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
