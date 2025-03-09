package RPCRequest;

import DTO.RPCRequest;
import DTO.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
