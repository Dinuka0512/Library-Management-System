package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.tm.BookReturningTm;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageBookReturning implements Initializable {
    @FXML
    private TableView<BookReturningTm> BookReturningTabel;

    @FXML
    private TableColumn<BookReturningTm, String> columnAction;

    @FXML
    private TableColumn<BookReturningTm, String> columnBookID;

    @FXML
    private TableColumn<BookReturningTm, String> columnBookName;

    @FXML
    private TableColumn<BookReturningTm, String> columnIssueDate;

    @FXML
    private TableColumn<BookReturningTm, String> columnIssueID;

    @FXML
    private TableColumn<BookReturningTm, String> columnIssueTime;

    @FXML
    private TableColumn<BookReturningTm, String> columnMemEmail;

    @FXML
    private TableColumn<BookReturningTm, String> columnMemID;

    @FXML
    private TableColumn<BookReturningTm, String> columnMemName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //INITIALIZING TABLE COLUMNS
        columnIssueID.setCellValueFactory(new PropertyValueFactory<>("issueID"));
        columnBookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        columnMemID.setCellValueFactory(new PropertyValueFactory<>("memID"));
        columnMemName.setCellValueFactory(new PropertyValueFactory<>("memName"));
        columnMemEmail.setCellValueFactory(new PropertyValueFactory<>("memEmail"));
        columnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        columnIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        columnIssueTime.setCellValueFactory(new PropertyValueFactory<>("issueTime"));
        columnAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        pageLoad();
    }

    private void pageLoad(){
        //LOAD THE TABLE DATA
        bookReturningTableLoad();
    }

    private void bookReturningTableLoad(){

    }
}
