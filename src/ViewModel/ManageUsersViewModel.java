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

public class ManageUsersViewModel {
    private ClientModelManager model;
    private List<Employee> employees;
    private PropertyChangeSupport propertyChangeSupport;

    public ManageUsersViewModel(ClientModelManager model) {
        this.model = model;
        employees = model.getEmployees();
        propertyChangeSupport = new PropertyChangeSupport(this);
        model.addListener("employees", this::employeesUpdated);
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

    public void deactivateEmployee(Employee employee) {
        model.deactivateEmployee(employee);
    }

    public void activateEmployee(Employee employee) {
        model.activateEmployee(employee);
    }

    public void updateEmployee(Employee employee, String newUsername, int newRoleId) {
        try {
            Employee employeeCopy = new Employee(employee.getEmployee_id(), newRoleId, newUsername);
            model.editEmployee(employeeCopy);
        } catch (SQLException e) {
            // handle exception
        }
    }

    public String validateInput(String username, String password, String selectedRole, boolean isEditMode, Employee editingUser) {
        if (username == null || username.isBlank()) {
            return "Please fill in all fields.";
        }
        if (!isEditMode && (password == null || password.isBlank())) {
            return "Please enter a password.";
        }
        if (selectedRole == null) {
            return "Please fill in all fields.";
        }

        for (Employee employee : employees) {
            if (employee.getUsername().equalsIgnoreCase(username)) {
                if (!isEditMode || (editingUser != null && !employee.equals(editingUser))) {
                    return "Username already exists.";
                }
            }
        }

        return null;
    }

    public void logOut() {
        model.logOut();
    }

    public void errorHandling(PropertyChangeEvent propertyChangeEvent) {
        propertyChangeSupport.firePropertyChange("cannotDeactivateEmployee", null, null);
    }
}
