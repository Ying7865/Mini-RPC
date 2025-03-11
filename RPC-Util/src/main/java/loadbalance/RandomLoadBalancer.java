package loadbalance;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalancer {
    @Override
    public String balance(List<String> addressList) {
        Random random = new Random();
        int targetIndex = random.nextInt(addressList.size());
        System.out.println("Load Balancer choose server: " + targetIndex);
        return addressList.get(targetIndex);
    }
}
