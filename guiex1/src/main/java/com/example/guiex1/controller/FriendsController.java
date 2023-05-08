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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FriendsController implements Observer<UtilizatorEntityChangeEvent> {
    public TableColumn <userDto,String>tableColumnUsername;
    public TableColumn <userDto,Date> tableColumnDate;
    public TableView<userDto> tableview;
    UtilizatorService service;
    Long id;
    ObservableList<userDto> model = FXCollections.observableArrayList();
    public void setUtilizatorService(UtilizatorService service,Long id) {
        this.service = service;
        this.id = id;
        service.addObserver(this);
        initModel();
    }
    @FXML
    public void initialize() {
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<userDto, String>("username"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<userDto, Date>("date"));
        tableview.setItems(model);
    }
    private void initModel() {
//        Iterable<Prietenie> prietenie = service.getAllPrietenii();
//        List<userDto> userdto = new ArrayList<userDto>();
//        for (Prietenie prieten : prietenie){
//            if (Objects.equals(prieten.getId1(), id) && Objects.equals(prieten.getStatus(), "requested"))
//                userdto.add(new userDto(service.getUserById(prieten.getId2()).getUsername(),prieten.getDate(),prieten.getStatus()));
//            if (Objects.equals(prieten.getId2(), id) && Objects.equals(prieten.getStatus(), "requested"))
//                userdto.add(new userDto(service.getUserById(prieten.getId1()).getUsername(),prieten.getDate(),prieten.getStatus()));
//        }

     //   return null;
//        List<Utilizator> usersList = StreamSupport.stream(users.spliterator(), false)
//                .collect(Collectors.toList());
//
//        //doar userii a caror prenume incepe cu "A"
//        List<Utilizator> usersFilteredList = usersList.stream().collect(Collectors.toList());
//
           model.setAll(service.all_confirmed(id));
    }
    public void update(UtilizatorEntityChangeEvent utilizatorEntityChangeEvent) {
        initModel();

    }

    public void delete(ActionEvent event) throws SQLException {
        userDto user = tableview.getSelectionModel().getSelectedItem();
        if (user!=null) {
            Prietenie prietenie2 = service.deletePrietenie(id,service.getUserByUsername(user.getUsername()).getId());
            Prietenie prietenie = service.deletePrietenie(service.getUserByUsername(user.getUsername()).getId(),id);


        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", " Prieten sters!");
    }

    public void add_friend(ActionEvent event) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/newfriend-view.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        stage.setWidth(600);
        NewFriendController newFriendController =  fxmlLoader.getController();
        newFriendController.setUtilizatorService(service,id);
        stage.show();
    }

    public void Back(ActionEvent event) throws IOException {
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
