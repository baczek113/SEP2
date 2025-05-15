package Network.RequestHandling;

import ClientModel.Requests.TaskSprintRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class AddTaskToSprintHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        TaskSprintRequest taskSprintRequest = (TaskSprintRequest) request;
        if(modelManager.addTaskToSprint(taskSprintRequest.getTask(), taskSprintRequest.getSprint())){
            // broadcast
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(taskSprintRequest.getSprint().getProject_id()));
        }
    }
}
