package Network;

import java.io.Serializable;

public abstract class Response implements Serializable {
    private String message;
    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
