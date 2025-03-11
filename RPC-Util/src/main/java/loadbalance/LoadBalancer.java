package loadbalance;

import java.util.List;

public interface LoadBalancer {
    String balance(List<String> addressList);
}
