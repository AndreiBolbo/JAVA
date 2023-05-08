package com.example.guiex1.controller;

import com.example.guiex1.Login;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.services.UtilizatorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    public TextField username;
    public TextField firstname;
    public TextField lastname;
    public PasswordField password;
    UtilizatorService service;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    public void setUtilizatorService(UtilizatorService service) {
        this.service = service;
        //service.addObserver((Observer<UtilizatorEntityChangeEvent>) this);
       // initModel();
    }
    public void registerdb(ActionEvent event) throws IOException {
        Utilizator user = new Utilizator(firstname.getText(),lastname.getText(),password.getText(),username.getText());
        service.addUtilizator(user);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/login-view.fxml"));
        AnchorPane userLayout = (AnchorPane) fxmlLoader.load();
        // AnchorPane root = (AnchorPane) FXMLLoader.load(Login.class.getResource("/packagename/hello-view.fxml"));
        stage.setScene(new Scene(userLayout));
        LoginController loginController = fxmlLoader.getController();
        loginController.setUtilizatorService(service);
        stage.show();
    }
}
