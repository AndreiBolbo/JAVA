package com.example.guiex1.controller;

import com.example.guiex1.Login;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.domain.ValidationException;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorController implements Observer<UtilizatorEntityChangeEvent> {


    UtilizatorService service;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();

    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,String> tableColumnFirstName;
    @FXML
    TableColumn<Utilizator,String> tableColumnLastName;
    @FXML
    TableColumn<Utilizator,String> tableColumnUsername;
    @FXML
    TableColumn<Utilizator,String> tableColumnPassword;
    public TableColumn<Utilizator,Long> tableColumnId;
    public void setUtilizatorService(UtilizatorService service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("username"));
        tableColumnPassword.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("password"));
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Utilizator, Long>("id"));
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<Utilizator> users = service.getAll();
        List<Utilizator> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());


        //doar userii a caror prenume incepe cu "A"
        List<Utilizator> usersFilteredList = usersList.stream().collect(Collectors.toList());

        model.setAll(usersFilteredList);
    }

    @Override
    public void update(UtilizatorEntityChangeEvent utilizatorEntityChangeEvent) {

        initModel();
    }

    public void handleAddUtilizator(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/register-view.fxml"));
        AnchorPane userLayout = (AnchorPane) fxmlLoader.load();
        // AnchorPane root = (AnchorPane) FXMLLoader.load(Login.class.getResource("/packagename/hello-view.fxml"));
        stage.setScene(new Scene(userLayout));
        RegisterController r = fxmlLoader.getController();
        r.setUtilizatorService(service);
        stage.show();
//        Utilizator user = new Utilizator("Alex","Bogdan","parola123","alexut");
//        try{
//            Utilizator saved = service.addUtilizator(user);
//        } catch (ValidationException e){
//            MessageAlert.showErrorMessage(null, e.getMessage());
//            return;
//        }
       // MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "User adaugat cu succes!");
//        if(service.addUtilizator(user) == null){
//            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "User adaugat cu succes!");
//        } else{
//            MessageAlert.showErrorMessage(null, "Failed adding user");
//        }
    }

    public void handleDeleteUtilizator(ActionEvent actionEvent) throws SQLException {
        Utilizator user=(Utilizator) tableView.getSelectionModel().getSelectedItem();
        if (user!=null) {
            service.DeleteAllFriends(user.getId());
            Utilizator deleted= service.deleteUtilizator(user.getId());
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "User sters cu succes!");
    }

    public void handleUpdateUtilizator(ActionEvent actionEvent) {
    }

    public void handleButton(ActionEvent actionEvent) {
        System.out.println("Hello :)");
    }

    public void onSayHello(ActionEvent actionEvent) {
        System.out.println("Hello ...");
    }

    public void tousers(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/login-view.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        LoginController loginController = fxmlLoader.getController();
        loginController.setUtilizatorService(service);
        stage.show();
    }


//    public void handleDeleteMessage(ActionEvent actionEvent) {
//        MessageTask selected = (MessageTask) tableView.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            MessageTask deleted = service.deleteMessageTask(selected);
//            if (null != deleted)
//                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Studentul a fost sters cu succes!");
//        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nici un student!");
//    }
//

//
//    @FXML
//    public void handleUpdateMessage(ActionEvent ev) {
//        MessageTask selected = tableView.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            showMessageTaskEditDialog(selected);
//        } else
//            MessageAlert.showErrorMessage(null, "NU ati selectat nici un student");
//    }
//
//    @FXML
//    public void handleAddMessage(ActionEvent ev) {
//
//        showMessageTaskEditDialog(null);
//    }
//
//    public void showMessageTaskEditDialog(MessageTask messageTask) {
//        try {
//            // create a new stage for the popup dialog.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/views/editMessageTaskView.fxml"));
//
//            AnchorPane root = (AnchorPane) loader.load();
//
//            // Create the dialog Stage.
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Edit Message");
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            //dialogStage.initOwner(primaryStage);
//            Scene scene = new Scene(root);
//            dialogStage.setScene(scene);
//
//            EditMessageTaskController editMessageViewController = loader.getController();
//            editMessageViewController.setService(service, dialogStage, messageTask);
//
//            dialogStage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
}
