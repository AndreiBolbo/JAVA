package com.example.guiex1.controller;

import com.example.guiex1.Login;
import com.example.guiex1.domain.Prietenie;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.services.UtilizatorService;
import com.example.guiex1.utils.events.UtilizatorEntityChangeEvent;
import com.example.guiex1.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NewFriendController implements Observer<UtilizatorEntityChangeEvent> {
    UtilizatorService service;
    Long id;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    public TableView<Utilizator> tableview;
    public TableColumn <Utilizator,String> tableColumnUsername;
    public TableColumn<Utilizator,String> tableColumnNume;
    public TableColumn <Utilizator,String> tableColumnPrenume;
    public void initialize() {
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("username"));
        tableview.setItems(model);
    }
    public void setUtilizatorService(UtilizatorService service, Long id) throws SQLException {
        this.service = service;
        service.addObserver(this);
        this.id = id;
        initModel();
    }
    private void initModel()  {

        try {
            model.setAll(service.AllUsers(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void save(ActionEvent event) throws SQLException {
        Utilizator user=(Utilizator) tableview.getSelectionModel().getSelectedItem();
        if (user!=null) {
            Prietenie deleted= service.add_friend(id, user.getId());
        }
    }

    @Override
    public void update(UtilizatorEntityChangeEvent utilizatorEntityChangeEvent) {
        initModel();
    }

    public void home(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/connect-view.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        stage.setWidth(600);
        ConnectController connectController = fxmlLoader.getController();
        connectController.setUtilizatorService(service,id);
        stage.show();
    }
}
