package com.example.guiex1.controller;

import com.example.guiex1.Login;
import com.example.guiex1.domain.Prietenie;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.domain.userDto;
import com.example.guiex1.services.UtilizatorService;
import com.example.guiex1.utils.events.UtilizatorEntityChangeEvent;
import com.example.guiex1.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class RequestController implements Observer<UtilizatorEntityChangeEvent> {
    public TableColumn<userDto,String> tableColumnUsername;
    public TableColumn <userDto, Date> tableColumnDate;
    public TableColumn <userDto,String>tableColumnStatus;
    public TableView <userDto> tableview;
    UtilizatorService service;
    Long id;
    ObservableList<userDto> model = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<userDto, String>("username"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<userDto, Date>("date"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<userDto, String>("status"));
        tableview.setItems(model);
    }
    public void setUtilizatorService(UtilizatorService service,Long id) {
        this.service = service;
        this.id = id;
        service.addObserver(this);
        initModel();
    }
    private void initModel() {

        model.setAll(service.all_requested(id));
    }
    @Override
    public void update(UtilizatorEntityChangeEvent utilizatorEntityChangeEvent) {
        initModel();
    }

    public void Confirm(ActionEvent event) throws SQLException {
        userDto user = tableview.getSelectionModel().getSelectedItem();
        if (user!=null) {
            Prietenie prietenie = service.updatePrietenie(service.getUserByUsername(user.getUsername()).getId(),id);

        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Sunteti prieteni!");

    }

    public void delete(ActionEvent event) throws SQLException {
        userDto user = tableview.getSelectionModel().getSelectedItem();
        if (user!=null) {
            Prietenie prietenie = service.deletePrietenie(service.getUserByUsername(user.getUsername()).getId(),id);

        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "cerere stearsa cu succes!");
    }

    public void Show(ActionEvent event) throws IOException {
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

    public void home(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/connect-view.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        stage.setWidth(600);

        ConnectController connectController = fxmlLoader.getController();
        connectController.setUtilizatorService(service,id);
        stage.show();
    }
}
