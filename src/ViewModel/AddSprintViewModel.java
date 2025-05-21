package ViewModel;

import ClientModel.ClientModelManager;
import DataModel.Project;

import java.beans.PropertyChangeSupport;
import java.sql.Date;

public class AddSprintViewModel {
    private final ClientModelManager model;
    private PropertyChangeSupport propertyChangeSupport;

    public AddSprintViewModel(ClientModelManager model){
        this.model = model;
    }

    public void addSprint(Project project, String name, Date startDate, Date endDate){
        model.addSprint(project, name, startDate, endDate);
    }
}
