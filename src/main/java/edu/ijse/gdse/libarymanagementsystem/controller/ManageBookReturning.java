package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.BookDto;
import edu.ijse.gdse.libarymanagementsystem.dto.tm.BookReturningTm;
import edu.ijse.gdse.libarymanagementsystem.model.BookModel;
import edu.ijse.gdse.libarymanagementsystem.model.ManageBookReturningModel;
import edu.ijse.gdse.libarymanagementsystem.model.ReturnBookModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
    private Slider slider;

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

    private BookReturningTm tabelDetails;
    private long daysLate;

    private BookDto bookDetail;
    private final int lateFeeforOneDay = 10;

    private double fee;
    private double feeLateFee;
    private double damageFee;

    @FXML
    private Label lblFullPayment;
    private ReturnBookModel returnBookModel  = new ReturnBookModel();

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

        //HERE LOAD THE BOOK DAMAGE PRICE
        loadBookDamagePrice(0);

        setSlider();
    }

    private void setSlider(){
        slider.setValue(0);

        //SET DAMAGE SIZE
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            String text;
            if(newValue.intValue() == 0){
                lblDamageSize.setTextFill(Color.GREEN);
            }else{
                lblDamageSize.setTextFill(Color.RED);
            }

            loadBookDamagePrice(newValue.intValue());
            lblDamageSize.setText(newValue.intValue() + "%");
        });
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
            tabelDetails = BookReturningTabel.getSelectionModel().getSelectedItem();
            if (tabelDetails != null) {
                bookDetail = bookModel.getBookDetails(tabelDetails.getBookId());

                lblBookId.setText(bookDetail.getBookId());
                lblBookName.setText(bookDetail.getName());
                lblQty.setText("1");
                lblBookPrice.setText(Double.toString(bookDetail.getPrice()));
                lblIssueDate.setText(tabelDetails.getIssueDate());

                daysLate = getDaysBetween(LocalDate.parse(tabelDetails.getIssueDate()), LocalDate.now());
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

    private void loadBookDamagePrice(int damageValue){


        if(tabelDetails != null){
            if(slider.getValue() != 0){
                //THERE HAVE DAMAGE
                damageFee = (bookDetail.getPrice() / 100) * damageValue;
            }
            if(daysLate < 0){
                //HAVE BOOK LATE
                feeLateFee = (daysLate * -1) * lateFeeforOneDay;
            }
        }

        fee = feeLateFee + damageFee;

        // Create a DecimalFormat instance with the desired format
        DecimalFormat df = new DecimalFormat("#.00");
        // Format the number
        String formattedPrice = df.format(fee);

        lblFullPayment.setText("Rs " + formattedPrice + " /=");
    }

    @FXML
    void payNow(ActionEvent event) {
        if(tabelDetails != null){
            try{
                boolean isSaved = manageBookReturningModel.returnBook(bookDetail, tabelDetails.getIssueID(), fee);
                if(isSaved){
                    pageLoad();
                    sendEmail();
                    new Alert(Alert.AlertType.CONFIRMATION,"Successful").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Something Went wrong").show();
                }
            }catch (SQLException e1){
                System.out.println("SQL Exception");
                e1.printStackTrace();
            }catch (ClassNotFoundException e2){
                System.out.println("Class Not Found Exception");
                e2.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"You must Select the book before return...").show();
        }
    }

    private void sendEmail(){
        /*Dear [Member_Name],
        We hope this message finds you well.
        We are writing to confirm the return of the following book(s) that you borrowed from our library:

        ** Book Title**: [Book_Name]
        ** Date Borrowed**: [Issue_Date]
        ** Return Date**: [Return_Date]
        ** Damage cost :
        ** Late Date Cots :

        Thank you for returning the book(s) on time. However, we noticed that the return was [daysLate] day(s) late.
        The late return fee, calculated as [daysLate] day(s) * Rs. [Late_Fee_Per_Day], amounts to Rs. [Total_Late_Fee].
        **Total Amount Paid**: Rs. [Total_Amount_Paid]

        We appreciate your timely settlement of the dues.
        If you have any questions or need further assistance, please do not hesitate to contact us.
        Thank you for your attention to this matter, and we look forward to your continued patronage.

        THANK YOU & WELLCOME!

        Best Regards,
        Gnanapradeepa Public Library
        Bandaragama
        0382 289 975
        */
    }
}
