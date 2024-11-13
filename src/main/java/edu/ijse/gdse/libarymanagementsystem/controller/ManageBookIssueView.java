package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.model.BookIssueModel;
import edu.ijse.gdse.libarymanagementsystem.model.BookModel;
import edu.ijse.gdse.libarymanagementsystem.model.MemberModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private Label lblBookName;

    @FXML
    private Label lblMemName;

    @FXML
    private ComboBox<String> comboBookId;

    @FXML
    private ComboBox<String> comboMemberId;

    private final MemberModel memberModel = new MemberModel();
    private final BookModel bookModel = new BookModel();
    private final BookIssueModel bookIssueModel = new BookIssueModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pageReload();
    }

    private void pageReload(){
        loardMemberIds();
        ladBookIds();

        //LOAD NEXT IDS
        generateIssueId();
    }

    //GENERATE NEXT IDS
    private void generateIssueId(){
        try{
            String nextId = bookIssueModel.getNextIssueId();
            System.out.println(nextId);
        }catch (SQLException e1){
            System.out.println("SQL EXCeption");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("Class Not Found Exception");
            e2.printStackTrace();
        }
    }

    // --------> LOAD COMBO BOX DATA
    private void ladBookIds(){
        //HERE LOAD THE BOOK IDS FOR COMBO BOX
        try{
            ArrayList<String> dto = bookModel.getAllBookIds();
            ObservableList<String> observableList= FXCollections.observableArrayList();
            for(String id : dto){
                observableList.add(id);
            }
            comboBookId.setItems(observableList);
        }catch (SQLException e1){
            System.out.println("SQL EXCeption");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("Class Not Found Exception");
            e2.printStackTrace();
        }
    }
    private void loardMemberIds(){
        //HERE LOAD THE MEMBER IDS FOR COMBO BOX
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


    //-------> LOAD THE COMBOBOX NAMES
    @FXML
    void comboBookNameLoad(ActionEvent event) {
        //HERE LOAD THE BOOK NAME TO LABEL
        try{
            String bookId = comboBookId.getValue();
            String name = bookModel.getBookName(bookId);
            lblBookName.setText(bookId + " | " + name);
        }catch (SQLException e1){
            System.out.println("SQL EXCeption");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("Class Not Found Exception");
            e2.printStackTrace();
        }
    }

    @FXML
    void comboMemberNameLoad(ActionEvent event) {
        //HERE LOAD THE MEMBER NAME TO LABEL
        try{
            String memId = comboMemberId.getValue();
            String name = memberModel.getMemberName(memId);
            lblMemName.setText(memId + " | " + name);
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
        //CAN GO TO HOME PAGE
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
