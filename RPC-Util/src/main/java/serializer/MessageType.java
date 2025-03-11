package serializer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageType {
    Request(0),Response(1);
    private int code;
    public int getCode() {
        return code;
    }
}
