package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.tm.TempBookIssueTm;
import edu.ijse.gdse.libarymanagementsystem.model.BookIssueModel;
import edu.ijse.gdse.libarymanagementsystem.model.BookModel;
import edu.ijse.gdse.libarymanagementsystem.model.MemberModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    private TableColumn<TempBookIssueTm, String> columnBookId;

    @FXML
    private TableColumn<TempBookIssueTm, String> columnBookName;

    @FXML
    private TableColumn<TempBookIssueTm, String> columnIssueId;

    @FXML
    private TableColumn<TempBookIssueTm, Integer> columnQty;

    @FXML
    private TableView<TempBookIssueTm> tableTempIssue;

    @FXML
    private Label lblBookQty;

    @FXML
    private Label lblIssueId;

    private final MemberModel memberModel = new MemberModel();
    private final BookModel bookModel = new BookModel();
    private final BookIssueModel bookIssueModel = new BookIssueModel();

    private String bookName;
    private ArrayList<TempBookIssueTm> tempBookIssuesArrayList = new ArrayList<>();
    private final int bookQtyCanGet = 3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnIssueId.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        columnBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        columnBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

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
            bookName = bookModel.getBookName(bookId);
            lblBookName.setText(bookId + " | " + bookName);
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

    @FXML
    void addNewBookToIssueTable(ActionEvent event) {
        if(comboBookId.getValue() != null){
            //HERE GET THE BOOK NAME

            for(TempBookIssueTm temp : tempBookIssuesArrayList){
                //HERE GOING TO CHECK IS THERE HAVE GET THE SAME BOOK?
                //IN THE ONE ISSUE ID CANNOT HAVE THE SAME TWO BOOKS

                if(temp.getBookId().equals(comboBookId.getValue())){
                    //IS THERE HAVE SAME NEED TO STOP THIS ISSUEING
                    new Alert(Alert.AlertType.ERROR, "THERE ALLREADY HAVE THIS BOOK!!").show();
                    return;
                }
            }

            System.out.println(tempBookIssuesArrayList.size());
            if((tempBookIssuesArrayList.size() + 1) <= bookQtyCanGet){
                TempBookIssueTm tempIssue = new TempBookIssueTm(
                        lblIssueId.getText(),
                        comboBookId.getValue(),
                        bookName,
                        Integer.parseInt(lblBookQty.getText())
                );

                tempBookIssuesArrayList.add(tempIssue);
                refresh();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Sorry You have reached to the limit! \nThe Member Can Only get the " + bookQtyCanGet + " Books For at one Time...").show();
                return;
            }
        }else{
            //IS NULL
            new Alert(Alert.AlertType.WARNING,"Please select the book Id!!!").show();
        }
    }

    private void refresh(){
        loardThetempIssueTable();
        clearTextIntempArea();
    }

    private void loardThetempIssueTable(){
        ObservableList<TempBookIssueTm> observableList = FXCollections.observableArrayList();
        for(TempBookIssueTm temp : tempBookIssuesArrayList){
           TempBookIssueTm tempBookIssueTm = new TempBookIssueTm(
                   temp.getIssueId(),
                   temp.getBookId(),
                   temp.getName(),
                   temp.getQty()
           );

           observableList.add(tempBookIssueTm);
        }

        tableTempIssue.setItems(observableList);
    }


    private void clearTextIntempArea(){
        comboBookId.setValue("");
        comboBookId.setPromptText("Select Book...");
    }

    @FXML
    void getTempTableValues(MouseEvent event) {
        TempBookIssueTm tempBookIssueTm = tableTempIssue.getSelectionModel().getSelectedItem();

        if(tempBookIssueTm != null){
            lblIssueId.setText(tempBookIssueTm.getIssueId());
            comboBookId.setValue(tempBookIssueTm.getBookId());
            lblBookQty.setText(Integer.toString(tempBookIssueTm.getQty()));
        }
    }


    @FXML
    void btnRemoveTemp(ActionEvent event) {

    }
}
