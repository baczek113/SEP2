package ViewModel;

import ClientModel.ClientModelManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModel {
    private ClientModelManager model;
    private StringProperty message;

    public LoginViewModel(ClientModelManager model) {
        this.model = model;
        this.message = new SimpleStringProperty("");
    }

    public void login(String username, String password) {
        model.login(username, password);
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
}
