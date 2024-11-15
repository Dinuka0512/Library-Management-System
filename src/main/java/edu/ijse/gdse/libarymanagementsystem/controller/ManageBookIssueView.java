package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.BookDto;
import edu.ijse.gdse.libarymanagementsystem.dto.MemberDto;
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
    private TableColumn<TempBookIssueTm, Button> columnButton;

    @FXML
    private TableView<TempBookIssueTm> tableTempIssue;

    @FXML
    private Label lblBookIdAndName;

    @FXML
    private Label lblBookNameload;

    @FXML
    private Label lblBookQtyLoad;

    @FXML
    private Label lblBookqty;

    @FXML
    private Label lblIssueId;

    @FXML
    private Label lblMemName;

    @FXML
    private Label lblMemberNameload;

    private final MemberModel memberModel = new MemberModel();
    private final BookModel bookModel = new BookModel();
    private final BookIssueModel bookIssueModel = new BookIssueModel();

    private BookDto bookDetail;
    private MemberDto memberDetails;
    private ArrayList<TempBookIssueTm> tempBookIssuesArrayList = new ArrayList<>();
    private final int bookQtyCanGet = 3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnIssueId.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        columnBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        columnBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnButton.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
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
            bookDetail = bookModel.getBookDetails(bookId);
            System.out.println(bookDetail);
            System.out.println(bookDetail.getName());
            lblBookIdAndName.setText(bookId + " | " + bookDetail.getName());

            //HERE LOAD THE SAMPLE DATA
            lblBookNameload.setText(bookDetail.getName());
            lblBookQtyLoad.setText(Integer.toString(bookDetail.getQty()));

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
            memberDetails = memberModel.getMemberDetails(memId);
            lblMemName.setText(memId + " | " + memberDetails.getName());
            lblMemberNameload.setText("Mr/Miss. " + memberDetails.getName());
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
                    //IS THERE HAVE SAME NEED TO STOP THIS ISSUING
                    new Alert(Alert.AlertType.ERROR, "THERE ALL READY HAVE THIS BOOK!!").show();
                    return;
                }
            }

            if((tempBookIssuesArrayList.size() + 1) <= bookQtyCanGet){
                //CREATE THE BUTTON AND WE CAN ACCESS IT WITH btn REFERENCE
                Button btn = new Button("Remove");

                TempBookIssueTm tempIssue = new TempBookIssueTm(
                        lblIssueId.getText(),
                        comboBookId.getValue(),
                        bookDetail.getName(),
                        Integer.parseInt(lblBookqty.getText()),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    //HERE REMOVE THIS FORM ARRAY LIST
                    tempBookIssuesArrayList.remove(tempIssue);
                    tableTempIssue.refresh();
                    //HERE LOAD THE TABLE AFTER CHANGES
                    loardThetempIssueTable();
                });

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
//        clearTextIntempArea();
    }

    private void loardThetempIssueTable(){
        ObservableList<TempBookIssueTm> observableList = FXCollections.observableArrayList();
        for(TempBookIssueTm temp : tempBookIssuesArrayList){
           TempBookIssueTm tempBookIssueTm = new TempBookIssueTm(
                   temp.getIssueId(),
                   temp.getBookId(),
                   temp.getName(),
                   temp.getQty(),
                   temp.getBtnRemove()
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
            lblBookqty.setText(Integer.toString(tempBookIssueTm.getQty()));
        }
    }
}
