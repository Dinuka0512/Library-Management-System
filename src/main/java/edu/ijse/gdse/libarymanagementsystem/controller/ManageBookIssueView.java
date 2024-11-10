package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.model.MemberModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageBookIssueView implements Initializable {

    @FXML
    private AnchorPane body;

    @FXML
    private ComboBox<String> comboBookId;

    @FXML
    private ComboBox<String> comboMemberId;

    private final MemberModel memberModel = new MemberModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loardMemberIds();
    }

    private void loardMemberIds(){
        try{
            ArrayList<String> dto = memberModel.getAllMemberIds();
            ObservableList<String> observableList= FXCollections.observableArrayList();
            for(String id : dto){
                observableList.add(id);
            }
            comboMemberId.setItems(observableList);
        }catch (SQLException e1){
            System.out.println("SQL EXCeption");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("Class Not Found Exception");
            e2.printStackTrace();
        }
    }

    @FXML
    void goToHomePage(MouseEvent event) {
        try{
            body.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
            body.getChildren().add(load);
        }catch (IOException e){
            System.out.println("IOException \nUnable to load home page");
            e.printStackTrace();
        }
    }

}
