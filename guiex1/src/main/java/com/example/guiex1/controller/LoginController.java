package com.example.guiex1.controller;

import com.example.guiex1.Login;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.services.UtilizatorService;
import com.example.guiex1.utils.events.UtilizatorEntityChangeEvent;
import com.example.guiex1.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;

public class LoginController {
    public Button connectb;
    public Button registerb;
    @FXML
    PasswordField password;
    @FXML
    TextField username;
    UtilizatorService service;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();
//    public void Connect(ActionEvent actionEvent) {
//
//    }
    public void setUtilizatorService(UtilizatorService service) {
        this.service = service;
        //service.addObserver((Observer<UtilizatorEntityChangeEvent>) this);
        initModel();
    }
    public void Register(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

       FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/register-view.fxml"));
      AnchorPane userLayout = (AnchorPane) fxmlLoader.load();
       // AnchorPane root = (AnchorPane) FXMLLoader.load(Login.class.getResource("/packagename/hello-view.fxml"));
        stage.setScene(new Scene(userLayout));
        RegisterController r = fxmlLoader.getController();
        r.setUtilizatorService(service);
         stage.show();
//        UtilizatorController utilizatorController = fxmlLoader.getController();
//        utilizatorController.setUtilizatorService(service);





    }
    public void Connect(ActionEvent event) throws IOException {
        Utilizator user = service.login(username.getText(), password.getText());
        if (user == null) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Username sau parola gresita!");
        }
      //   FXMLLoader fxmlLoader = new FXMLLoader();
     //   fxmlLoader.setLocation(getClass().getResource("com/example/guiex1/views/UtilizatorView.fxml"));
      //  Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("com/example/guiex1/views/UtilizatorView.fxml")));
//        UtilizatorController userController = fxmlLoader.getController();
//        userController.setUtilizatorService(service);
        else {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
            FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/connect-view.fxml"));
            AnchorPane userLayout = fxmlLoader.load();
            stage.setScene(new Scene(userLayout));
            stage.setWidth(600);

            ConnectController connectController = fxmlLoader.getController();
            connectController.setUtilizatorService(service,user.getId());
            stage.show();
        }

    }

    private void initModel() {

    }

    public void admin(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/UtilizatorView.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        UtilizatorController utilizatorController = fxmlLoader.getController();
        utilizatorController.setUtilizatorService(service);
        stage.setWidth(600);
        stage.show();
    }
}
