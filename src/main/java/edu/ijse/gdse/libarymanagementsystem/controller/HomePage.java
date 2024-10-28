package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.UserDto;
import edu.ijse.gdse.libarymanagementsystem.model.UserModel;
import edu.ijse.gdse.libarymanagementsystem.util.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomePage implements Initializable {

    private UserModel userModel = new UserModel();

    @FXML
    private TextField txtNewUserName;

    @FXML
    private AnchorPane workSpace;

    @FXML
    private TextField txtPasswordConfirm;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblPrivacy;

    @FXML
    private Label lblWellcome;

    @FXML
    private Label currentUName;

    @FXML
    private TextField txtCurruntPassword;

    @FXML
    private AnchorPane anchorPrivacy;

    @FXML
    private TextField txtNewPassword;

    @FXML
    private Button btnExit;

    private UserDto userDetails;

    @Setter
    private AnchorPane dashboardBody;


    private String userEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        getUserDetails();
//        serPrivacyWindow();
    }

    public void setUserDetails(String email){
        try{
            if(DashBoardContro.isAdminLogin()){
                //DTO IS NULL ...(admin has log in)

                lblWellcome.setText("Wellcome admin,");
            }else{
                //DTO IS NOT NULL... (user has login)
                userDetails = userModel.getUserDetails(email);
                lblWellcome.setText("Wellcome " + userDetails.getName() + ",");
                currentUName.setText(userDetails.getName());
            }

//            if(!DashBoardContro.isAdminLogin()){
//                currentUName.setText(userDetails.getName());
//            }
        }catch (ClassNotFoundException e1){
            System.out.println("class not Found");
            e1.printStackTrace();
        } catch (SQLException e2){
            System.out.println("Sql queree error");
            e2.printStackTrace();
        } catch (Exception e3){
            e3.printStackTrace();
        }
    }

    //EXIT BUTTON
    @FXML
    void closePrivacyWindow(ActionEvent event) {
        anchorPrivacy.setVisible(false);
    }

    @FXML
    void openPrivacyWindow(MouseEvent event) {
        // IS ADMIN IS LOGED -> setVisible(true) methord is not working adn give allert...
        if(DashBoardContro.isAdminLogin()){
            new Alert(Alert.AlertType.CONFIRMATION,"SORRY!\nAdmin user name or password cannot be change!...").show();
        }else{
            anchorPrivacy.setVisible(true);
        }
    }


    //SAVE BUTTON
    @FXML
    void saveNewDetails(ActionEvent event) throws SQLException, ClassNotFoundException{

//        boolean isValidName = txtNewUserName.getText().matches("^[A-Za-z ]+$");
        boolean isValidName = Validation.isValidName(txtNewUserName.getText());
        if(isValidName && !txtNewUserName.getText().equals(null)){
            //CHECK USERNAME IS VALID

            if(txtCurruntPassword.getText().equals(userDetails.getPassword())){
                //CHECK IS OLD PASSWORD IS SAME

//                boolean isValidPassword = txtNewPassword.getText().matches("^[a-zA-Z0-9]{4,30}$");
                boolean isValidPassword = Validation.isValidPassword(txtNewPassword.getText());
                if(isValidPassword && !txtNewPassword.getText().equals(null)){
                    //CHECK IS PASSWORD IS VALID

                    if(txtNewPassword.getText().equals(txtPasswordConfirm.getText())){
                        //CHECK IS THE BOTH ARE SAME OR NOT
                        //HERE NOW ALL ARE OK----------------
                        updateUser();
                    }else{
                        new Alert(Alert.AlertType.CONFIRMATION,"New Password and Current Password must be same!!..").show();
                    }
                }else{
                    new Alert(Alert.AlertType.CONFIRMATION,"PLEASE ENTER VALID PASSWORD \nPassword cannot be Null\n* You can add only letters & numbers(A-Z , a-z, 0-9) \n* Password must be 4 characters \n* password not shuld longerThan 30 characters...").show();
                }
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Wrong Password, \nYou need to enter current password..").show();
            }
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"CHECK YOUR NEW USER NAME \n* User name canot be null \n* User name can have only charecters (A-Z , a-z)").show();
        }
    }


    private void updateUser(){
        try{
            boolean isUpdated = userModel.updateUser(txtNewUserName.getText(),txtNewPassword.getText(),userDetails.getEmail());
            if(isUpdated){
                //saved
                new Alert(Alert.AlertType.CONFIRMATION,"Successfully updated!").show();
                clearText();
                //when successfully updated we need to auto maticaly go to login page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
                Stage window = (Stage) txtCurruntPassword.getScene().getWindow();
                window.setScene(new Scene(loader.load()));

            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Failed").show();
            }

        }catch (ClassNotFoundException e1){
            System.out.println("Class Not found Exception");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("Sql Exception");
            e2.printStackTrace();
        }catch (IOException e3){
            System.out.println("IOExeption");
            e3.printStackTrace();
        }
    }
    //end hear-

    private void clearText(){
        txtCurruntPassword.setText("");
        txtNewPassword.setText("");
        txtPasswordConfirm.setText("");
        txtNewUserName.setText("");
    }



    @FXML
    void addNewUser(ActionEvent event) {
        try{
            Parent fxmlLorder = FXMLLoader.load(getClass().getResource("/view/CreateAccount.fxml"));
            Scene scene = new Scene(fxmlLorder);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Create New Account");
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();

        }catch (IOException e){
            System.out.println("faild to loard Create Account");
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}
