package com.example.guiex1.controller;

import com.example.guiex1.Login;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.domain.userDto;
import com.example.guiex1.services.UtilizatorService;
import com.example.guiex1.utils.events.UtilizatorEntityChangeEvent;
import com.example.guiex1.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConnectController {
    UtilizatorService service;
    Long id;

    public void setUtilizatorService(UtilizatorService service,Long id) {
        this.service = service;
        this.id = id;
    }


    public void search(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/request-view.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        stage.setWidth(600);
        RequestController requestController =  fxmlLoader.getController();
        requestController.setUtilizatorService(service,id);
        stage.show();
    }

    public void show_friends(ActionEvent event) throws IOException {
        System.out.println(id);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/friends-view.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        FriendsController friendsController =  fxmlLoader.getController();
        friendsController.setUtilizatorService(service,id);
        stage.show();


    }

    public void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/login-view.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        LoginController loginController = fxmlLoader.getController();
        loginController.setUtilizatorService(service);
        stage.show();
    }
}


