package Network.RequestHandling;

import ClientModel.Requests.AddTaskRequest;
import ClientModel.Requests.Request;
import Model.Project; // <-- Add import
import Model.Sprint;  // <-- Add import
import Network.ServerModelManager;

public class AddTaskRequestHandler implements RequestHandlerStrategy {

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        AddTaskRequest addTaskRequest = (AddTaskRequest) request;

        Sprint sprint = addTaskRequest.getSprint(); // This can be null for backlog tasks
        Project project = addTaskRequest.getProject(); // <-- Get the project directly from the request

        if (project == null) {
            System.err.println("AddTaskRequestHandler: Project cannot be null in AddTaskRequest. Task not added.");
            return; // Or throw an error, or return a failure response if this handler sent responses
        }

        // The ServerModelManager.addTask method already handles if the sprint is null.
        // Its signature is: addTask(Sprint sprint, Project project, String title, String description, int priority)
        System.out.println("AddTaskRequestHandler: Processing task '" + addTaskRequest.getName() + "' for project '" + project.getName() + "'" + (sprint != null ? " and sprint '" + sprint.getName() + "'" : " (backlog)"));

        if (modelManager.addTask(
                sprint,                     // Pass the sprint (can be null)
                project,                    // Pass the project object
                addTaskRequest.getName(),   // This is the task title
                addTaskRequest.getDescription(),
                addTaskRequest.getPriority()))
        {
            System.out.println("AddTaskRequestHandler: Task '" + addTaskRequest.getName() + "' successfully processed by modelManager.");
            // Potentially broadcast updates here if your architecture requires it
        } else {
            System.err.println("AddTaskRequestHandler: modelManager.addTask call returned false for task '" + addTaskRequest.getName() + "'.");
        }
    }
}