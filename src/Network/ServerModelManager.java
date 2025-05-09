package Network;

import Model.Project;
import Model.Request;
import Model.Sprint;
import Model.Task;

import java.util.List;

public class ServerModelManager {

    private List<Project> projects;
    private List<Sprint> sprints;
    private List<Task> tasks;

    private ActionStrategy strategy;


    public ServerModelManager() {}

    public void setStrategy(ActionStrategy strategy){this.strategy = strategy;}

    public void handleRequest(Request request){
        String action = request.getAction().toLowerCase();

//        if(action.equals("create")){setStrategy(new );} implement Strategy pattern
    }

}
