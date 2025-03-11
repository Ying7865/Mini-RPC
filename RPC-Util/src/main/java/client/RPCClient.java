package client;

import dto.RPCRequest;
import dto.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
