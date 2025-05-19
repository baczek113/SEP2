package ViewModel;

import ClientModel.ClientModelManager;
import Model.Employee;
import Model.Project;
import Model.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ManageUsersViewModel
{
    private ClientModelManager model;
    private List<Employee> employees;
    private PropertyChangeSupport propertyChangeSupport;


    public ManageUsersViewModel (ClientModelManager model)
    {
        this.model = model;
        employees = model.getEmployees();
        propertyChangeSupport = new PropertyChangeSupport(this);
        model.addListener("employees",this::employeesUpdated);

    }

    public void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void employeesUpdated(PropertyChangeEvent e) {
        employees = (List<Employee>) e.getNewValue();
        propertyChangeSupport.firePropertyChange("projects", null, null);
    }
    public ObservableList<Employee> getEmployees() {
        ObservableList<Employee> projectsObservable = FXCollections.observableArrayList();
        projectsObservable.setAll(employees);
        return projectsObservable;
    }
    public void addEmployee(String username, String password, int role) {
        model.createEmployee(username, password, role);
    }

    public void deactivateEmployee(Employee employee)
    {
        model.deactivateEmployee(employee);
    }
    public void activateEmployee(Employee employee)
    {
        model.activateEmployee(employee);
    }

    public void updateEmployee(Employee employee) {

        model.editEmployee(employee);
    }
    public void updateEmployee(Employee employee, String newUsername, int newRoleId) {
        employee.setUsername(newUsername);

        // Replace role object with new one
        String roleName = roleNameFromId(newRoleId);
        employee.setRole(new Role(newRoleId, roleName));

        model.editEmployee(employee); // send to server
    }
    private String roleNameFromId(int id) {
        return switch (id) {
            case 1 -> "admin";
            case 2 -> "product_owner";
            case 3 -> "scrum_master";
            case 4 -> "developer";
            default -> "unknown";
        };
    }






}
