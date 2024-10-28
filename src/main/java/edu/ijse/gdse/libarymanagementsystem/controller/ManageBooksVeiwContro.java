package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.model.AuthorModel;
import edu.ijse.gdse.libarymanagementsystem.model.CategoryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageBooksVeiwContro implements Initializable {

    @FXML
    private TextField txtBookName;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<String> comboAuthorId;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lblWellcome;

    @FXML
    private ComboBox<String> comboCategoryId;

    @FXML
    private TextField txtBookPrice;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblBookId;

    @FXML
    private TextField txtBookQty;

    @FXML
    private AnchorPane body;
    private AuthorModel authorModel = new AuthorModel();
    private CategoryModel categoryModel = new CategoryModel();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loardAuthorIds();
        loardColumnIds();
    }

    private void loardAuthorIds(){
        ArrayList<String> authorIds = authorModel.getAllAuthorIds();
        if(authorIds != null){
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(authorIds);
            comboAuthorId.setItems(observableList);
        }
    }

    private void loardColumnIds(){
        ArrayList<String> categoryId = categoryModel.getAllCategoryIds();
        if(categoryId != null){
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(categoryId);
            comboCategoryId.setItems(observableList);
        }
    }
}