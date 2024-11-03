package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.AuthorDto;
import edu.ijse.gdse.libarymanagementsystem.dto.BookDto;
import edu.ijse.gdse.libarymanagementsystem.dto.CategoryDto;
import edu.ijse.gdse.libarymanagementsystem.dto.tm.BookTm;
import edu.ijse.gdse.libarymanagementsystem.model.*;
import edu.ijse.gdse.libarymanagementsystem.util.Validation;
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

import java.net.URL;
import java.sql.SQLException;
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
    private Label lblCategoryName;

    @FXML
    private Label lblAuthorName;

    @FXML
    private AnchorPane anchorAddAuthor;

    @FXML
    private Label lblAuthorId;

    @FXML
    private TextField txtAuthorName;

    @FXML
    private TextField txtCategoryName;

    @FXML
    private Label lblCategoryId;

    @FXML
    private TextField txtAuthorAddress;

    @FXML
    private TextField txtAuthorContact;

    @FXML
    private TextField txtAuthorEmail;

    @FXML
    private AnchorPane anchorAddCategory;

    @FXML
    private TableColumn<BookTm, String> columnAuthor;

    @FXML
    private TableColumn<BookTm, String> columnBookId;

    @FXML
    private TableColumn<BookTm, String> columnBookName;

    @FXML
    private TableColumn<BookTm, String> columnCategory;

    @FXML
    private TableColumn<BookTm, Double> columnPrice;

    @FXML
    private TableColumn<BookTm, Integer> columnQty;

    @FXML
    private TableView<BookTm> tableView;

    @FXML
    private AnchorPane body;
    private final AuthorModel authorModel = new AuthorModel();
    private final CategoryModel categoryModel = new CategoryModel();
    private final BookModel bookModel = new BookModel();
    private final AuthorBookModel authorBookModel = new AuthorBookModel();
    private final BookCategoryModel bookCategoryModel = new BookCategoryModel();
    private final ManabeBooksViewModel manabeBooksViewModel = new ManabeBooksViewModel();

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
        columnBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        columnBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("authorName"));

        pageReset();
    }

    private void pageReset(){
        loardTable();

        //GENERATE THE IDS
        loardNextBookId();
        loardNextCategoryId();
        loardNextAuthorId();

        //LOARD COMBO BOX DATA
        loardAuthorIds();
        loardCategoryIds();
    }

    private void loardTable(){
        try{
            //THIS ARRAYS LIST CREATE FOR GRAB THE ALL DATA NEED TO TABLE
            ArrayList<BookTm> bookTms = new ArrayList<>();

            ArrayList<BookDto> res = bookModel.getAllBooks();
            //RUN THE FOR EACH LOOP FOR WHILE THE Book dtos...

            for(BookDto dto : res){
                //GET THE AUTHOR NAME
                String authorId = authorBookModel.getAuthorId(dto.getBookId());

                String authorName;
                if(authorId != null){
                    authorName = authorModel.getAuthorName(authorId);
                }else{
                    authorName = " - ";
                }

                //GET THE CATEGORY NAME
                String categoryId = bookCategoryModel.getCategoryId(dto.getBookId());

                String categoryName;
                if(categoryId != null){
                    categoryName = categoryModel.getCateName(categoryId);
                }else{
                    categoryName = " - ";
                }

                BookTm bookTm = new BookTm(
                        dto.getBookId(),
                        dto.getName(),
                        dto.getQty(),
                        dto.getPrice(),
                        categoryName,
                        authorName
                );

                bookTms.add(bookTm);
            }

            ObservableList<BookTm> observableBookTMS = FXCollections.observableArrayList();
            for(BookTm dto : bookTms){
                BookTm bookTm = new BookTm(
                        dto.getBookId(),
                        dto.getName(),
                        dto.getQty(),
                        dto.getPrice(),
                        dto.getCategoryName(),
                        dto.getAuthorName()
                );

                observableBookTMS.add(bookTm);
            }

            tableView.setItems(observableBookTMS);

        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }

    }

    @FXML
    void comboCategoryId(ActionEvent event) {
        //WHEN WE SELECT CATEGORY ID FROM COMBO BOX PRINT NAME ON SCREEN
        try{
            if(comboCategoryId.getValue() != null){
                String cateName = categoryModel.getCateName(comboCategoryId.getValue());
                lblCategoryName.setText(comboCategoryId.getValue() + " | " + cateName);
            }
        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }

    @FXML
    void comboAuthorId(ActionEvent event) {
        //WHEN WE SELECT AUTHOR ID FROM COMBO BOX PRINT NAME ON SCREEN
        try{
            if(comboAuthorId.getValue() != null){
                String authorName = authorModel.getAuthorName(comboAuthorId.getValue());
                lblAuthorName.setText(comboAuthorId.getValue() + " | " + authorName);
            }
        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }

    @FXML
    void openAddNewAuthor(ActionEvent event) {
        anchorAddAuthor.setVisible(true);
    }

    @FXML
    void openAddNewCategory(ActionEvent event) {
        anchorAddCategory.setVisible(true);
    }

    @FXML
    void closeAuthor(ActionEvent event) {
        anchorAddAuthor.setVisible(false);
    }

    @FXML
    void closeCategory(ActionEvent event) {
        anchorAddCategory.setVisible(false);
    }


    //SET THE AUTHOR IDS TO COMBO BOX
    private void loardAuthorIds(){
        ArrayList<String> authorIds = authorModel.getAllAuthorIds();
        if(authorIds != null){
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(authorIds);
            comboAuthorId.setItems(observableList);
        }
    }

    //SET THE CATEGORY IDS TO COMBO BOX
    private void loardCategoryIds(){
        ArrayList<String> categoryId = categoryModel.getAllCategoryIds();
        if(categoryId != null){
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(categoryId);
            comboCategoryId.setItems(observableList);
        }
    }




    //LOARD IDS
    private void loardNextBookId(){
        try{
            String newId = bookModel.getNextBookId();
            lblBookId.setText(newId);
        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }

    private void loardNextAuthorId(){
        try{
            String newID = authorModel.genarateAuthorId();
            lblAuthorId.setText(newID);
        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }

    private void loardNextCategoryId(){
        try{
            String newID = categoryModel.generateCategoryID();
            lblCategoryId.setText(newID);
        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }



    //NEW AUTHOR SAVE HEAR
    @FXML
    void saveAuthor(ActionEvent event) {
        isAuthorReadyToSave();
    }

    private void isAuthorReadyToSave(){
        if(Validation.isValidName(txtAuthorName.getText()) && !txtAuthorName.getText().equals(null)){
            //CHECK IS VALID NAME

            if(Validation.isValidEmail(txtAuthorEmail.getText()) && !txtAuthorEmail.getText().equals(null)){
                //CHECK IS IT VALID EMAIL

                if(Validation.isValidName(txtAuthorAddress.getText()) && !txtAuthorAddress.getText().equals(null)){
                    //CHECK IS IT VALID NAME

                    if(Validation.isValidMobileNumber(txtAuthorContact.getText()) && !txtAuthorContact.getText().equals(null)){
                        //CHECK IS IT VALID MOBILE NUMBER

                        //ALL OK, NOW SAVE NEW AUTHOR ....
                        saveNewAuthor();

                    }else{
                        new Alert(Alert.AlertType.CONFIRMATION,"Please Enter the Valid mobile Number!!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.CONFIRMATION,"CHECK ADDRESS!, Please Enter the valid City name!").show();
                }
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"PLEASE ENTER VALID EMAIL!!!").show();
            }
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"CHECK NAME!\nPlease Input valid name..\n(name only can have letters)").show();
        }
    }
    private void saveNewAuthor(){
        AuthorDto authorDto = new AuthorDto(
                lblAuthorId.getText(),
                txtAuthorName.getText(),
                txtAuthorEmail.getText(),
                txtAuthorAddress.getText(),
                txtAuthorContact.getText()
        );

        try{
            boolean res = authorModel.saveNewAuthor(authorDto);

            if(res){
                clearAuthorText();
                pageReset();
                new Alert(Alert.AlertType.CONFIRMATION,"Author saved Sucsessfully!").show();
                anchorAddAuthor.setVisible(false);
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Failed").show();
            }

        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }

    private void clearAuthorText(){
        loardNextAuthorId();
        txtAuthorName.setText("");
        txtAuthorAddress.setText("");
        txtAuthorContact.setText("");
        txtAuthorEmail.setText("");
    }


    //CATEGORY SAVE HERE
    @FXML
    void categorySave(ActionEvent event) {
        isReadyToSaveCategory();
    }

    private void isReadyToSaveCategory(){
        if(Validation.isValidName(txtCategoryName.getText()) && !txtCategoryName.getText().equals(null)){
            //CHECK NAME IS VALID
            //ALL ARE OK NOW
            saveCategory();
        }
    }

    private void saveCategory(){
        CategoryDto categoryDto = new CategoryDto(
                lblCategoryId.getText(),
                txtCategoryName.getText()
        );

        try{
            boolean isSaved = categoryModel.saveNewCategory(categoryDto);
            if(isSaved){
                clearCategoryText();
                pageReset();
                new Alert(Alert.AlertType.CONFIRMATION,"Category Successfuly saved").show();
                anchorAddCategory.setVisible(false);
            }
        }catch(ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }

    private void clearCategoryText(){
        txtCategoryName.setText("");
    }



    //BOOK SAVE HEAR
    @FXML
    void saveMain(ActionEvent event) {
        isReadyToSave();
    }

    private void isReadyToSave() {
        if(Validation.isValidName(txtBookName.getText()) && !txtBookName.getText().equals(null)){
            //CHECK THE BOOK NAME

            if(!txtBookQty.getText().equals(null) && Validation.isValidInteger(txtBookQty.getText())){
                //CHECK THE BOOK QTY

                if(!txtBookPrice.getText().equals(null) && Validation.isValidDouble(txtBookPrice.getText())){
                    //CHECK THE BOOK PRICE

                    if(comboAuthorId.getValue() != null){
                        //CHECK IS THE AUTHOR ID SELECTED!
                        if(comboCategoryId.getValue() != null){
                            //CHECK IS THE CATEGORY ID IS SELECTED!
                            //ALL ARE OK NOW
                            save();
                        }else{
                            new Alert(Alert.AlertType.CONFIRMATION,"Pleace select the Category Id").show();
                        }
                    }else{
                        new Alert(Alert.AlertType.CONFIRMATION,"Pleace select the Author Id").show();
                    }
                }else{
                    new Alert(Alert.AlertType.CONFIRMATION,"PLESE ENTER VALID PRICE \nEx - (1,2,3...) Numbers Can Only added").show();
                }
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"PLESE ENTER VALID QUANTITY \nEx - (1,2,3...) Numbers Can Only added").show();
            }
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"CHECK NAME!! \nPlease Enter valid name").show();
        }
    }
    private void save() {
        BookDto bookDto = new BookDto(
          lblBookId.getText(),
          txtBookName.getText(),
          Integer.parseInt(txtBookQty.getText()),
          Double.parseDouble(txtBookPrice.getText())
        );

        try{

            String res = manabeBooksViewModel.saveNewBook(
                    bookDto,
                    comboAuthorId.getValue(),
                    comboCategoryId.getValue()
            );

            if(res.equals("saved Successfully")){
                clearAllTexts();
                pageReset();
            }
            new Alert(Alert.AlertType.CONFIRMATION,res).show();
            //SHOWING THE MASSAGE AS ALERT, WHAT CAME FROM MODEL CLASS

        }catch (SQLException e1){
            System.out.println("SQLException");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("ClassNotFoundException");
            e2.printStackTrace();
        }
    }

    private void clearAllTexts(){
        loardNextBookId();
        txtBookName.setText("");
        txtBookQty.setText("");
        txtBookPrice.setText("");

        //CATEGORY COMBO BOX
        comboCategoryId.getSelectionModel().clearSelection();
        comboCategoryId.setPromptText("Select Category...");

        //AUTHOR COMBO BOX
        comboAuthorId.getSelectionModel().clearSelection();
        comboAuthorId.setPromptText("Select Author...");

        lblAuthorName.setText("");
        lblCategoryName.setText("");
    }
}