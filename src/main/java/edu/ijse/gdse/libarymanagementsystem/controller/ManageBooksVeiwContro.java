package edu.ijse.gdse.libarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManageBooksVeiwContro{

    @FXML
    private AnchorPane body;

    @FXML
    void loarDashboard(MouseEvent event) {
        try{
            body.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
            body.getChildren().add(load);
        }catch(Exception e){
            System.out.println("Unable to loard DashBoard on Manage Books");
            e.printStackTrace();
        }
    }
}