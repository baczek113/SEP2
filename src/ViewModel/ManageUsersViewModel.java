package ViewModel;

import ClientModel.ClientModelManager;
import DataModel.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
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
        model.addListener("cannotDeactivateEmployee", this::errorHandling);
        model.addListener("cannotChangeRole", this::cannotChangeRole);
    }

    private void cannotChangeRole(PropertyChangeEvent propertyChangeEvent) {
        propertyChangeSupport.firePropertyChange("cannotChangeRole", null, null);
    }

    public void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void employeesUpdated(PropertyChangeEvent e) {
        employees = (List<Employee>) e.getNewValue();
        propertyChangeSupport.firePropertyChange("employees", null, null);
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

    public void updateEmployee(Employee employee, String newUsername, int newRoleId) {
        try {
            Employee employeeCopy = new Employee(employee.getEmployee_id(), newRoleId, newUsername);
            model.editEmployee(employeeCopy);
        } catch (SQLException e) {
//            ...
        }
    }
    private String roleNameFromId(int id) {
        return switch (id) {
            case 2 -> "product_owner";
            case 3 -> "scrum_master";
            case 4 -> "developer";
            default -> "unknown";
        };
    }

    public void logOut()
    {
        model.logOut();
    }

    public void errorHandling(PropertyChangeEvent propertyChangeEvent)
    {
        propertyChangeSupport.firePropertyChange("cannotDeactivateEmployee", null, null);
    }

}
