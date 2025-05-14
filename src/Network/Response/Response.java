package Network.Response;

import java.io.Serializable;

public abstract class Response implements Serializable {
    private String statusMessage;
    public Response(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getMessage() {
        return statusMessage;
    }
}
