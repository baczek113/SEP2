package ViewModel;

import ClientModel.ClientModelManager;
import Model.Employee;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel {
    private final ClientModelManager model;
    private PropertyChangeSupport propertyChangeSupport;

    public LoginViewModel(ClientModelManager model) {
        this.model = model;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        model.addListener("login", this::loginResponse);
    }

    public void loginResponse(PropertyChangeEvent event)
    {
        propertyChangeSupport.firePropertyChange("login", null, event.getNewValue());
    }

    public void login(String username, String password)
    {
        model.login(username, password);
    }

    public void addListener(String name, PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(name, listener);
    }
}
