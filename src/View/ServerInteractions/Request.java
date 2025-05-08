package View.ServerInteractions;

import java.io.Serializable;

public abstract class Request implements Serializable
{
    private String action;

    public Request(String action)
    {
        this.action = action;
    }

    public String getAction()
    {
        return action;
    }
}
