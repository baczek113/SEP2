package ClientModel;

import ClientModel.ServerInteractions.*;
import Model.Employee;
import Model.Project;
import Model.Sprint;
import Model.Task;
import Network.Response.Response;

import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ClientModelManager {
    private PropertyChangeSupport propertyChangeSupport;
    private List<Employee> employees;
    private List<Project> projects;
    private List<Sprint> sprints;
    private List<Task> tasks;
    private Employee loggedEmployee;

    public ClientModelManager()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
        employees = new ArrayList<>();
        projects = new ArrayList<>();
        sprints = new ArrayList<>();
        tasks = new ArrayList<>();
        loggedEmployee = null;
    }

    public void handleServerResponse(Response response)
    {
        String message = response.getMessage();

        switch (message)
        {

        }
    }

    public void addSprint(Project project, String name, String description, Date startDate, Date endDate)
    {
        Request request = new AddSprintRequest("addSprint", loggedEmployee, project, name, description, startDate, endDate);

        //Send request :PPP
    }

    public void addTask(Sprint sprint, String name, String description, int priority)
    {
        Request request = new AddTaskRequest("addTask", loggedEmployee, sprint, name, description, priority);

        //Send request :PPP
    }

    public void reloadTasks(Sprint sprint)
    {
        Request request = new SprintRequest("getTasks", loggedEmployee, sprint);

        //Send request and update ArrayList<> :PPP
    }

    public void reloadProjects()
    {
        Request request = new Request("getProjects", loggedEmployee);

        //Send request and update ArrayList<> :PPP
    }

    public void reloadSprints(Project project)
    {
        Request request = new ProjectRequest("getSprints", loggedEmployee, project);

        //Send request and update ArrayList<> :PPP
    }
}
