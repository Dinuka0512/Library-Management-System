package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.BookDto;
import edu.ijse.gdse.libarymanagementsystem.dto.SupplierDto;
import edu.ijse.gdse.libarymanagementsystem.dto.tm.BookTm;
import edu.ijse.gdse.libarymanagementsystem.dto.tm.SupplierTm;
import edu.ijse.gdse.libarymanagementsystem.model.BookModel;
import edu.ijse.gdse.libarymanagementsystem.model.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageSuppliersView implements Initializable {

    @FXML
    private ComboBox<String> comboBookId;

    @FXML
    private TableColumn<SupplierTm, String> columnAddress;

    @FXML
    private TableColumn<SupplierTm, String> columnContact;

    @FXML
    private TableColumn<SupplierTm, String> columnEmail;

    @FXML
    private TableColumn<SupplierTm, String> columnName;

    @FXML
    private TableColumn<SupplierTm, String> columnSupplierId;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblSupplierId;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TableView<SupplierTm> tableSupplier;
    private final SupplierModel supplierModel = new SupplierModel();
    private final BookModel bookModel = new BookModel();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //HERE INITIALIZE THE TABLE COLUMNS
        columnSupplierId.setCellValueFactory(new PropertyValueFactory<>("suppierId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("emial"));

        pageLoad();
    }

    private void pageLoad(){
        //LOAD THE TABLE
        loadTable();

        //LOAD THE COMBO BOX VALUES
        loadComboBoxValues();

        //GENARATE THE NEXT SUPPLIER IDS
        loardNextSupplierIds();
    }

    private void loadTable(){
        //HERE LOAD THE TABLE
        try{
            ArrayList<SupplierDto> dtos = supplierModel.getAllSuppliers();
            ObservableList<SupplierTm> supplierTms = FXCollections.observableArrayList();
            for (SupplierDto dto : dtos){
                SupplierTm supplierDto = new SupplierTm(
                        dto.getSupplierId(),
                        dto.getName(),
                        dto.getContact(),
                        dto.getAddress(),
                        dto.getEmail()
                );

                supplierTms.add(supplierDto);
            }
            //SET THE TABLE ITEMS
            tableSupplier.setItems(supplierTms);

        }catch (SQLException e1){
            System.out.println("SQLException");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("ClassNotFoundException");
            e2.printStackTrace();
        }
    }

    private void loadComboBoxValues(){
        try{
            ArrayList<String> bookIds = bookModel.getAllBookIds();
                if(bookIds != null){
                ObservableList<String> observableList = FXCollections.observableArrayList();
                observableList.addAll(bookIds);
                comboBookId.setItems(observableList);
                }

        }catch (SQLException e1){
            System.out.println("SQLException");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("ClassNotFoundException");
            e2.printStackTrace();
        }
    }

    @FXML
    void onClickCombo(ActionEvent event) {
        try {
            if (comboBookId.getValue() != null) {
                BookDto bookDetails = bookModel.getBookDetails(comboBookId.getValue());
                if(bookDetails != null){
                    lblBookName.setText(comboBookId.getValue() + " | " + bookDetails.getName());
                }else{
                    lblBookName.setText("BOOK NOT FOUND!");
                }
            }
        }catch (SQLException e1){
            System.out.println("SQLException");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("ClassNotFoundException");
            e2.printStackTrace();
        }
    }

    private void loardNextSupplierIds(){
        //HERE LOAD THE NEXT SUPPLIER IDS
        try{
            String newId = supplierModel.loardNextSupplierId();
            lblSupplierId.setText(newId);
        }catch (ClassNotFoundException e1){
            System.out.println("Class Not Found Exception");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQL Exception");
            e2.printStackTrace();
        }
    }


    @FXML
    void getDataOnClick(MouseEvent event) {
        SupplierTm dto = tableSupplier.getSelectionModel().getSelectedItem();
        if(dto != null){
            lblSupplierId.setText(dto.getSuppierId());
            txtName.setText(dto.getName());
            txtAddress.setText(dto.getAddress());
            txtContact.setText(dto.getContact());
            txtEmail.setText(dto.getEmial());
        }
    }


    private void pageRefresh(){
        //CLEAR THE ALL TEXT
        loardNextSupplierIds();

        txtName.setText("");
        txtName.setPromptText("name");

        txtEmail.setText("");
        txtEmail.setPromptText("email");

        txtContact.setText("");
        txtContact.setPromptText("contact");

        txtAddress.setText("");
        txtAddress.setPromptText("address");

        comboBookId.setValue("");
    }

    @FXML
    void resetthePage(ActionEvent event) {
        loadTable();
        pageRefresh();
    }

    public void getDataOnClick(javafx.scene.input.MouseEvent mouseEvent) {

    }


//    private boolean isReadyToSaveSupplier(){
//        if(Validation.isValidName(txtSuplier Name.getText())){
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
