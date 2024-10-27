package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.UserDto;
import edu.ijse.gdse.libarymanagementsystem.model.BranchModel;
import edu.ijse.gdse.libarymanagementsystem.model.CreateAccountModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateAccountContro implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            genarateId();
            loardBranchIds();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //create object create Account Modek
    private CreateAccountModel cAccountModel = new CreateAccountModel();
    private BranchModel branchModel = new BranchModel();

    @FXML
    private Button btnCreate;

    @FXML
    private Label labelUserId;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtBranchId;

    @FXML
    private ComboBox<String> comboBoxBranchId;

    @FXML
    private TextField txtCPw;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private AnchorPane body;

    private void loardBranchIds() throws ClassNotFoundException, SQLException{
        ArrayList<String> branchIds = branchModel.getAllBranchId();
        // Convert ArrayList to ObservableList
        ObservableList<String> observableBranchIds = FXCollections.observableArrayList(branchIds);
        comboBoxBranchId.setItems(observableBranchIds);
    }

    @FXML
    void createAccount(ActionEvent event) throws ClassNotFoundException, SQLException {
        if(comboBoxBranchId.getValue() == null){
            new Alert(Alert.AlertType.CONFIRMATION,"Please Select the branch ID!").show();
        }else{
            //BRANCH ID HAS SELECTED

            boolean nameValidation = txtName.getText().matches("^[A-Za-z ]+$");
            if(nameValidation && !txtName.getText().equals(null)){
                //CHECK NAME IS IT NOT NULL AND IS IT CORRECT NAME

                boolean cityNameValidation = txtAddress.getText().matches("^[A-Za-z ]+$");
                if(cityNameValidation && !txtAddress.getText().equals(null)){
                    //CHECK THE CITY ADDRESS AND IS IT VALID NAME

                    if(cAccountModel.isUniqueEmail(txtEmail.getText()) && !txtEmail.getText().equals(null)){
                        //UNIQUE EMAIL
                        boolean isValidEmail = txtEmail.getText().matches(".+\\@.+\\..+");
                        if(isValidEmail && !txtEmail.getText().equals(null)){
                            //CHECK THE EMAIL AND IS IT VALID EMAIL

                            boolean isValidPassword = txtPassword.getText().matches("^[a-zA-Z0-9]{4,30}$");
                            if(isValidPassword && !txtPassword.getText().equals(null)){
                                //CHECK PASSWORD IS VALID AND IS THE VALID PASSWORD

                                if(txtPassword.getText().equals(txtCPw.getText())){
                                    //CHECK IS THE PASSWORD AND CURRENT PASSWORD IS IT BOTH SAME..
                                    //HEAR All are OK ------------------------

                                    saveNewUser();

                                }else{
                                    new Alert(Alert.AlertType.CONFIRMATION,"Your password and Current password must be same!!..").show();
                                }
                            }else{
                                new Alert(Alert.AlertType.CONFIRMATION,"PLEASE ENTER VALID PASSWORD \nPassword cannot be Null\n* You can add only letters & numbers(A-Z , a-z, 0-9) \n* Password must be 4 characters \n* password not shuld longerThan 30 characters...").show();
                            }
                        }else{
                            new Alert(Alert.AlertType.CONFIRMATION,"SOMTHING WENT WRONG...\nYou must add Vaid Email address").show();
                        }
                    }else{
                        new Alert(Alert.AlertType.CONFIRMATION, "This Email is Allready have or \n( Email cannot be Null )").show();
                    }
                }else{
                    new Alert(Alert.AlertType.CONFIRMATION,"CHECK ADDRESS\n\n* Address Canot be Null \n* Address only can have the letters").show();
                }
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"CHECK NAME\n\n* Name Canot be Null \n* Name only can have the letters").show();
            }
        }
    }

    private void saveNewUser(){
        UserDto dto = new UserDto(
             labelUserId.getText(),
             txtName.getText(),
             txtAddress.getText(),
             txtPassword.getText(),
             txtEmail.getText()
        );

        try{
            String resp = cAccountModel.saveUser(dto, comboBoxBranchId.getValue());
            new Alert(Alert.AlertType.INFORMATION,resp).show();
                if(resp.equals("Added Successfully")){
                    clearText();
                    Stage stage = (Stage) body.getScene().getWindow();
                    stage.close();
                }
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("User saving error");
            e.printStackTrace();
        }
    }

    private void genarateId(){
        try{
            String id = cAccountModel.genarateId();
            labelUserId.setText(id);
        }
        catch (Exception e1){
            e1.printStackTrace();
        }
    }

    private void clearText(){
        genarateId();
        txtName.setText("");
        txtAddress.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtCPw.setText("");
    }
}