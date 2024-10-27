package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.model.LogInModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginContro {

    //create Login model object
    private LogInModel logInModel = new LogInModel();

    @FXML
    private ImageView bgImageView;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtEmail;


    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label labelError;

    @FXML
    private AnchorPane body;

    @FXML
    void UserLogIn(ActionEvent event)throws ClassNotFoundException, SQLException , IOException{
        boolean checkEmail = logInModel.checkEmail(txtEmail.getText());
        if (checkEmail || txtEmail.getText().equals("admin")) {
            boolean checkPassword = logInModel.checkPassword(txtEmail.getText(), txtPassword.getText());
            if (checkPassword || txtPassword.getText().equals("admin123")) {
                String email = txtEmail.getText();
                if(email.equals("admin")){
                    DashBoardContro.setIsAdminLogin(true);
                }else{
                    DashBoardContro.setIsAdminLogin(false);
                    DashBoardContro.setUserEmail(email);
                }

                clearText();
                //here we need to open the Dashboard page... (On this stage)
                openDashboard();
            }else{
                labelError.setText("Try Again, Invalid Email or Password!");
            }
        }else{
            labelError.setText("Try again, Invalid Email!");
        }
    }

    private void clearText(){
        txtEmail.setText("");
        txtPassword.setText("");
    }

    private void openDashboard() throws IOException {
        try{
            body.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
            body.getChildren().add(load);
            Stage stage = (Stage) body.getScene().getWindow();
            stage.setTitle("Gnanapradeepa Library");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
