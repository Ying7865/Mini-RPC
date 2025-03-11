package loadbalance;

import java.util.List;

public class RoundLoadBalance implements LoadBalancer{
    private int targetIndex;
    @Override
    public String balance(List<String> addressList) {
        targetIndex++;
        targetIndex = targetIndex % addressList.size();
        return addressList.get(targetIndex);
    }
}
