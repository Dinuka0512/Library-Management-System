package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.*;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageBooksVeiwContro implements Initializable {

    @FXML
    private TextField txtBookName;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<String> comboAuthorId;

    @FXML
    private ComboBox<String> comboBookShelfId;

    @FXML
    private ComboBox<String> comboSupplierId;

    private String currentSupplierId;

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
    private Label lblBookShelfName;

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
    private AnchorPane anchorBookShelf;

    @FXML
    private Label lblBookShelfId;

    @FXML
    private TextField txtBookShelfLocation;

    @FXML
    private ComboBox<String> comboSectionId;

    @FXML
    private Label lblSectionName;

    @FXML
    private Label lblSupplierName;

    @FXML
    private Label lblSectionId;

    @FXML
    private AnchorPane body;

    @FXML
    private TextField txtSectionName;

    @FXML
    private AnchorPane anchorSection;

    //SUPPLIER

//    @FXML
//    private AnchorPane anchorSupplier;
//
//    @FXML
//    private Label lblSupplierId;
//
//    @FXML
//    private TextField txtSuplierName;
//
//    @FXML
//    private TextField txtSupplierAddress;
//
//    @FXML
//    private TextField txtSupplierContact;
//
//    @FXML
//    private TextField txtSupplierEmail;
//
//    @FXML
//    private TextField txtSuppliedQty;


    private final AuthorModel authorModel = new AuthorModel();
    private final CategoryModel categoryModel = new CategoryModel();
    private final BookShelfModel bookShelfModel = new BookShelfModel();
    private final SectionModel sectionModel = new SectionModel();
    private final BookModel bookModel = new BookModel();
    private final AuthorBookModel authorBookModel = new AuthorBookModel();
    private final BookCategoryModel bookCategoryModel = new BookCategoryModel();
    private final ManabeBooksViewModel manabeBooksViewModel = new ManabeBooksViewModel();
//    private final SupplierModel supplierModel = new SupplierModel();
//    private final BookSupplyModel bookSupplyModel = new BookSupplyModel();

    //HERE SAVE NEW SECTION
    @FXML
    void saveSection(ActionEvent event) {
        if(!txtSectionName.getText().equals("")){
            if(Validation.isValidName(txtSectionName.getText())){
                saveNewSection();
            }else{
                new Alert(Alert.AlertType.WARNING,"PLEASE ENTER VALID NAME").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please Enter Section Name").show();
        }
    }

    private void saveNewSection(){
        SectionDto sectionDto = new SectionDto(
                lblSectionId.getText(),
                txtSectionName.getText()
        );

        try{
            boolean isSaved = sectionModel.saveSection(sectionDto);
            if(isSaved){
                pageReset();
                clearTextInSection();
                anchorSection.setVisible(false);
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Save Failed \nSomthing went wrong").show();
            }
        }catch (SQLException e1){
            System.out.println("SQLException");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("ClassNotFoundException");
            e2.printStackTrace();
        }
    }

    private void clearTextInSection(){
        txtSectionName.setText("");
    }



    //HERE SAVE NEW BOOKSHELF...
    @FXML
    void bookShelfSave(ActionEvent event) {
        if(!txtBookShelfLocation.getText().equals("")){
            if(comboSectionId.getValue() != null){
                saveNewBookShelf();
            }else{
                new Alert(Alert.AlertType.WARNING, "Please Select The Section Id..").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Enter the shelf Location..").show();
        }
    }

    private void saveNewBookShelf(){
        BookshelfDto bookshelfDto = new BookshelfDto(
                lblBookShelfId.getText(),
                txtBookShelfLocation.getText(),
                comboSectionId.getValue()
        );

        try{
            boolean isSaved = bookShelfModel.saveBokShelf(bookshelfDto);
            if(isSaved){
                clearTextInBookshelf();
                pageFormat();
                pageReset();
                new Alert(Alert.AlertType.CONFIRMATION,"Save Successfully!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Save failed \nSomething Went Wrong!").show();
            }
        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }




    private void clearTextInBookshelf(){
        txtBookShelfLocation.setText("");
        txtBookShelfLocation.setPromptText("location");

        comboSectionId.setValue("");
        comboSectionId.setPromptText("Select section...");
    }

    @FXML
    void addNewSection(ActionEvent event) {
        anchorSection.setVisible(true);
    }
    @FXML
    void closeAddSection(ActionEvent event) {
        anchorSection.setVisible(false);
    }

    @FXML
    void addBookShelf(ActionEvent event) {
        anchorBookShelf.setVisible(true);
    }

    @FXML
    void bookShelfExit(ActionEvent event) {
        anchorBookShelf.setVisible(false);
    }

    @FXML
    void loarDashboard(MouseEvent event) {
        try{
            body.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
            body.getChildren().add(load);
        }catch(IOException e){
            System.out.println("Unable to loard DashBoard on Manage Books");
            e.printStackTrace();
        }
    }

    @FXML
    void resetTxt(ActionEvent event) {
        pageReset();
        pageFormat();
        clearAllTexts();
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

    @FXML
    void onClick(MouseEvent event) {
        //CLICKED THE TABLE ROW ----> Get Values From Table
        BookTm bookTm = tableView.getSelectionModel().getSelectedItem();
        if(bookTm != null){
            pageFormat();

            lblBookId.setText(bookTm.getBookId());
            txtBookName.setText(bookTm.getName());
            txtBookQty.setText(Integer.toString(bookTm.getQty()));
            txtBookPrice.setText(Double.toString(bookTm.getPrice()));

            try{
                String categoryId = categoryModel.getCategoryId(bookTm.getCategoryName());
                String authorId = authorModel.getAuthorIds(bookTm.getAuthorName());
                String bookShelfId = bookModel.getBookShelfId(bookTm.getBookId());
//                BookSuplyDto supplyDto = bookSupplyModel.getAll(bookTm.getBookId());

                if(categoryId != null){
                    comboCategoryId.setValue(categoryId);
                }else{
                    comboCategoryId.setValue("CATEGORY ID NOT FOUND");
                }

                if(authorId != null){
                    comboAuthorId.setValue(authorId);
                }else{
                    comboAuthorId.setValue("AUTHOR ID NOT FOUND");
                }

                if(bookShelfId != null){
                    comboBookShelfId.setValue(bookShelfId);
                }else{
                    comboBookShelfId.setValue("BOOK SHELF IS NOT FOUND");
                }

//                if(supplyDto != null){
//                    comboSupplierId.setValue(supplyDto.getSupplier_Id());
//                    currentSupplierId = supplyDto.getSupplier_Id();
//                    //SET TEXT TO txtSuppliedQty
//                    txtSuppliedQty.setText(Integer.toString(supplyDto.getQty()));
//                }else {
//                    comboSupplierId.setValue("SUPPLIER IS NOT FOUND");
//                }

            }catch (ClassNotFoundException e1){
                System.out.println("Class Not Found ");
                e1.printStackTrace();
            }catch (SQLException e2){
                System.out.println("SQL Exception");
                e2.printStackTrace();
            }

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnSave.setDisable(true);
        }
    }


    private void pageFormat(){
        anchorSection.setVisible(false);
        anchorAddCategory.setVisible(false);
        anchorBookShelf.setVisible(false);
        anchorAddAuthor.setVisible(false);
    }
    private void pageReset(){
        loardTable();

        //GENERATE THE IDS
        loardNextBookId();
        loardNextCategoryId();
        loardNextAuthorId();
        loardNextBookShelfId();
        loardNextSectionId();
//        loardNextSupplierIds();

        //LOARD COMBO BOX DATA
        loardAuthorIds();
        loardCategoryIds();
        loardBookShelfIds();
        loadSectionId();
//        loardSupplierIds();

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
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
    void comboBookShelfId(ActionEvent event) {
        //WHEN WE SELECT BookShelf ID FROM COMBO BOX PRINT NAME ON SCREEN
        try{
            if(comboBookShelfId.getValue() != null){
                String bookShelfLocation = bookShelfModel.getBookShelfLocation(comboBookShelfId.getValue());
                lblBookShelfName.setText(comboBookShelfId.getValue() + " | " + bookShelfLocation);
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
    void comboSectionId(ActionEvent event) {
        //WHEN WE SELECT SectionId ID FROM COMBO BOX PRINT NAME ON SCREEN
        try{
            if(comboSectionId.getValue() != null){
                String sectionName = sectionModel.getSectionName(comboSectionId.getValue());
                lblSectionName.setText(comboSectionId.getValue() + " | " + sectionName);
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

    private void loardBookShelfIds(){
        ArrayList<String> bookshelfId = bookShelfModel.getAllBookShelfIds();
        if(bookshelfId != null){
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(bookshelfId);
            comboBookShelfId.setItems(observableList);
        }
    }

    private void loadSectionId(){
        ArrayList<String> sectionIds = sectionModel.getAllSectionIds();
        if(sectionIds != null){
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(sectionIds);
            comboSectionId.setItems(observableList);
        }
    }

//    private void loardSupplierIds(){
//        try{
//            ArrayList<String> dtos = supplierModel.getAllSupplierIds();
//            if(dtos != null){
//                ObservableList<String> observableList = FXCollections.observableArrayList();
//                observableList.addAll(dtos);
//                comboSupplierId.setItems(observableList);
//            }
//        }catch (ClassNotFoundException e1){
//            System.out.println("Class Not found Exception");
//            e1.printStackTrace();
//        }catch (SQLException e2){
//            System.out.println("SQL Exception");
//            e2.printStackTrace();
//        }
//    }




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

    private void loardNextBookShelfId(){
        try{
            String id = bookShelfModel.generateNextId();
            lblBookShelfId.setText(id);
        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }

    private void loardNextSectionId(){
        try{
            String id = sectionModel.generateNextId();
            lblSectionId.setText(id);
        }catch (ClassNotFoundException e1){
            System.out.println("ClassNotFoundException");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQLException");
            e2.printStackTrace();
        }
    }

//    private void loardNextSupplierIds(){
//        //HERE LOAD THE NEXT SUPPLIER IDS
//        try{
//            String newId = supplierModel.loardNextSupplierId();
//            lblSupplierId.setText(newId);
//        }catch (ClassNotFoundException e1){
//            System.out.println("Class Not Found Exception");
//            e1.printStackTrace();
//        }catch (SQLException e2){
//            System.out.println("SQL Exception");
//            e2.printStackTrace();
//        }
//    }



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
                        new Alert(Alert.AlertType.WARNING,"Please Enter the Valid mobile Number!!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.WARNING,"CHECK ADDRESS!, Please Enter the valid City name!").show();
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"PLEASE ENTER VALID EMAIL!!!").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"CHECK NAME!\nPlease Input valid name..\n(name only can have letters)").show();
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
                pageFormat();
                new Alert(Alert.AlertType.CONFIRMATION,"Author saved Successfully!").show();
                anchorAddAuthor.setVisible(false);
            }else{
                new Alert(Alert.AlertType.ERROR,"Author Saving Failed").show();
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
                pageFormat();
                new Alert(Alert.AlertType.CONFIRMATION,"Category Successfuly saved").show();
                anchorAddCategory.setVisible(false);
            }else{
                new Alert(Alert.AlertType.ERROR,"Category Saving Failed").show();
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
        boolean isReady = isReadyToSave();
        if(isReady){save();}
    }

    private boolean isReadyToSave() {
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
                            if(comboBookShelfId.getValue() != null){
                                //CHECK IS THE BOOKSHELF ID SELECTED!
                                        return true;
                            }else{
                                new Alert(Alert.AlertType.WARNING,"Please select the BookShelf Id").show();
                                return false;
                            }
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Pleace select the Category Id").show();
                            return false;
                        }
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Pleace select the Author Id").show();
                        return false;
                    }
                }else{
                    new Alert(Alert.AlertType.WARNING,"PLESE ENTER VALID PRICE \nEx - (1,2,3...) Numbers Can Only added").show();
                    return false;
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"PLESE ENTER VALID QUANTITY \nEx - (1,2,3...) Numbers Can Only added").show();
                return false;
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"CHECK NAME!! \nPlease Enter valid name").show();
            return false;
        }
    }
    private void save() {
        BookDto bookDto = new BookDto(
            lblBookId.getText(),
            txtBookName.getText(),
            Integer.parseInt(txtBookQty.getText()),
            Double.parseDouble(txtBookPrice.getText()),
            comboBookShelfId.getValue()
        );

        try{

            String res = manabeBooksViewModel.saveNewBook(
                    bookDto,
                    comboAuthorId.getValue(),
                    comboCategoryId.getValue()
//                    comboSupplierId.getValue(),
//                    Integer.parseInt(txtSuppliedQty.getText())
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
        clearTextInBookshelf();
        clearTextInSection();
        txtBookName.setText("");
        txtBookQty.setText("");
        txtBookPrice.setText("");

        txtBookName.setPromptText("name");
        txtBookQty.setPromptText("qty");
        txtBookPrice.setPromptText("price");

        //CATEGORY COMBO BOX
        comboCategoryId.getSelectionModel().clearSelection();
        comboCategoryId.setPromptText("Select Category...");

        //AUTHOR COMBO BOX
        comboAuthorId.getSelectionModel().clearSelection();
        comboAuthorId.setPromptText("Select Author...");

        comboBookShelfId.getSelectionModel().clearSelection();
        comboBookShelfId.setPromptText("Select BookShelf...");

        lblAuthorName.setText("");
        lblCategoryName.setText("");
        lblBookShelfName.setText("");
    }

    @FXML
    void deleteBook(ActionEvent event) {
        //BOOK DELETE HERE
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to delete, Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            /* WHEN WE CLICKED THIS BUTTON WE ASK FROM THE USER
            *  -----> ARE YOU SURE ?
            *   AND CONFORM IS YES GIVE ACCESS TO DELETE THE BOOK....
            * */

            try{
                boolean isDelete = bookModel.deleteBook(lblBookId.getText());
                if(isDelete){
                    pageReset();
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted!..").show();
                    clearAllTexts();
                }

            }catch (ClassNotFoundException e1){
                System.out.println("Class Not found Exception");
                e1.printStackTrace();
            }catch (SQLException e2){
                System.out.println("SQL Exception");
                e2.printStackTrace();
            }
        }
    }

    @FXML
    void updateBook(ActionEvent event) {
        //UPDATE BOOK HERE
        boolean isReady = isReadyToSave();
        if(isReady){
            update();
        }
    }

    private void update(){
        BookTm dto = new BookTm(
                lblBookId.getText(),
                txtBookName.getText(),
                Integer.parseInt(txtBookQty.getText()),
                Double.parseDouble(txtBookPrice.getText()),
                comboCategoryId.getValue(),
                comboAuthorId.getValue()
        );

        try{
            boolean isUpdate = manabeBooksViewModel.updateBook(dto,comboBookShelfId.getValue(), comboCategoryId.getValue(), comboAuthorId.getValue());
            if(isUpdate){
                clearAllTexts();
                pageReset();
                new Alert(Alert.AlertType.CONFIRMATION,"Updated Successfully..!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Updating Failed").show();
            }
        }catch (ClassNotFoundException e1){
            System.out.println("Class not Found Exception");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQL Exception");
            e2.printStackTrace();
        }
    }

//    private boolean isReadyToSaveSupplier(){
//        if(Validation.isValidName(txtSuplierName.getText())){
//            //CHECK SUPPLIER NAME
//            if(Validation.isValidMobileNumber(txtSupplierContact.getText())){
//                //CHECK SUPPLIER CONTACT
//                if(Validation.isValidName(txtSupplierAddress.getText())){
//                    //CHECK SUPPLIER ADDRESS
//                    if(Validation.isValidEmail(txtSupplierEmail.getText())){
//                        //CHECK SUPPLIER EMAIL
//                        if(isEmailIsUnique()){
//                            //HERE CHECK THE EMAIL IS IT UNIQUE
//                            if(Validation.isValidInteger(txtSuppliedQty.getText())){
//                                //CHECK THE IS IT INTEGER ?
//                                return true;
//                            }else{
//                                new Alert(Alert.AlertType.WARNING, "You must add the numbers \nyou can't add the letters..\nExample - (1 - 9) \n*Can not be Zero (0)*").show();
//                                return false;
//                            }
//                        }else{
//                            new Alert(Alert.AlertType.ERROR,"This Email is All ready Have...").show();
//                            return false;
//                        }
//                    }else{
//                        new Alert(Alert.AlertType.WARNING,"Please Enter the Valid Email \nEmail canot be null").show();
//                        return false;
//                    }
//                }else{
//                    new Alert(Alert.AlertType.WARNING, "Please check the City name, \nAddress Canot be Null").show();
//                    return false;
//                }
//            }else {
//                new Alert(Alert.AlertType.WARNING, "Please Enter Valid Contact").show();
//                return false;
//            }
//        }else{
//            new Alert(Alert.AlertType.WARNING, "Please Enter Valid Name \nNames Only can have letters \nEx - (A-z").show();
//            return false;
//        }
//    }
//
//    private boolean isEmailIsUnique(){
//        try{
//            boolean isEmailUnique = supplierModel.isEmailUnique(txtSupplierEmail.getText());
//            if(isEmailUnique){
//                //true - Unique
//                return true;
//            }
//            //false - AllReady Have
//            return false;
//        }catch (ClassNotFoundException e1){
//            System.out.println("Class Not found Exception");
//            e1.printStackTrace();
//        }catch (SQLException e2){
//            System.out.println("Sql Exception");
//            e2.printStackTrace();
//        }
//        return false;
//    }
//
//    @FXML
//    void btnSaveSupplier(ActionEvent event) {
//        if(isReadyToSaveSupplier()){
//            SupplierDto dto = new SupplierDto(
//                    lblSupplierId.getText(),
//                    txtSuplierName.getText(),
//                    txtSupplierContact.getText(),
//                    txtSupplierAddress.getText(),
//                    txtSupplierEmail.getText()
//            );
//
//            try{
//                boolean isSaved = supplierModel.saveSupplier(dto);
//                if(isSaved){
//                    pageReset();
//                    clearTextSupplier();
//                    anchorSupplier.setVisible(false);
//                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Saved").show();
//                }else{
//                    new Alert(Alert.AlertType.ERROR,"Supplier Saving failed \nSomething Went wrong...").show();
//                }
//            }catch (ClassNotFoundException e1){
//                System.out.println("Class Not Found Exception");
//                e1.printStackTrace();
//            }catch (SQLException e2){
//                System.out.println("SQL Exception");
//                e2.printStackTrace();
//            }
//        }
//    }
}