package ViewModel;

import ClientModel.ClientModelManager;
import DataModel.Employee;

public class ViewProjectViewModel {
    private final ClientModelManager model;

    public ViewProjectViewModel(ClientModelManager model) {
        this.model = model;
    }

    public Employee getLogedEmployee ()
    {
        return model.getLoggedEmployee();
    }
}
