package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.BookDto;
import edu.ijse.gdse.libarymanagementsystem.dto.tm.BookReturningTm;
import edu.ijse.gdse.libarymanagementsystem.model.BookModel;
import edu.ijse.gdse.libarymanagementsystem.model.ManageBookReturningModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;


public class ManageBookReturning implements Initializable {


    @FXML
    private Label lbltodayDate;

    @FXML
    private Label lblBookId;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblBookPrice;

    @FXML
    private Label lblDamageSize;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblIssueDate;

    @FXML
    private Label lblLateDates;


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
    private final ManageBookReturningModel manageBookReturningModel = new ManageBookReturningModel();
    private final BookModel bookModel = new BookModel();

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

        pageLoad();
    }

    private void pageLoad(){
        //LOAD THE TABLE DATA
        bookReturningTableLoad();

        //HERE SET THE TODAY DATE
        setTodayDate();
    }

    private void setTodayDate(){
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();
        lbltodayDate.setText(date);
    }

    private void bookReturningTableLoad(){
        try{
            ArrayList<BookReturningTm> res = manageBookReturningModel.loadTabel();
            ObservableList<BookReturningTm> observableList = FXCollections.observableArrayList();
            for(BookReturningTm dto : res){
                observableList.add(dto);
            }

            BookReturningTabel.setItems(observableList);

        }catch (SQLException e1){
            System.out.println("SQL Exception");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("Class Not Found Exception ");
            e2.printStackTrace();
        }
    }

    @FXML
    void getTableDetails(MouseEvent event) {
        try {
            BookReturningTm tabelDetails = BookReturningTabel.getSelectionModel().getSelectedItem();
            if (tabelDetails != null) {
                BookDto bookDetail = bookModel.getBookDetails(tabelDetails.getBookId());

                lblBookId.setText(bookDetail.getBookId());
                lblBookName.setText(bookDetail.getName());
                lblQty.setText("1");
                lblBookPrice.setText(Double.toString(bookDetail.getPrice()));
                lblIssueDate.setText(tabelDetails.getIssueDate());

                long daysLate = getDaysBetween(LocalDate.parse(tabelDetails.getIssueDate()), LocalDate.now());
                String text;
                if (daysLate >= 0) {
                    lblLateDates.setTextFill(Color.GREEN);
                    text = "Have More " + daysLate + " Days";
                } else {
                    lblLateDates.setTextFill(Color.RED);
                    text = "Late " + daysLate * (-1) + " Days";
                }
                lblLateDates.setText(text);
            }
        }catch (SQLException e1){
            System.out.println("SQL Exception");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("Class Not Found Exception ");
            e2.printStackTrace();
        }
    }

    public long getDaysBetween(LocalDate startDate, LocalDate endDate) {
        long daysBetween = 0;

        // Iterate from startDate to endDate
        LocalDate date = startDate;
        while (date.isBefore(endDate)) {
            date = date.plusDays(1);
            daysBetween++;
        }

        return 14 - daysBetween;
    }
}
