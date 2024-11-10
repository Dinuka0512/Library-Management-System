package edu.ijse.gdse.libarymanagementsystem.controller;

import edu.ijse.gdse.libarymanagementsystem.dto.UserDto;
import edu.ijse.gdse.libarymanagementsystem.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashBoardContro implements Initializable  {

    public UserDto dto;
    //CAN GET ALL USERS DETAILS



    //IS ADMIN LOGIN------
    @Getter
    private static boolean isAdminLogin;

    public static void setIsAdminLogin(boolean isAdminLogin) {
        DashBoardContro.isAdminLogin = isAdminLogin;
    }
    //-----



    private UserModel userModel = new UserModel();

    @Getter
    private static String userEmail;

    public static void setUserEmail(String userEmail) {
        DashBoardContro.userEmail = userEmail;
    }


    @FXML
    private AnchorPane authorAnch;

    @FXML
    private AnchorPane bookAnch;

    @FXML
    private AnchorPane btnNamesAnchor;

    @FXML
    private AnchorPane cupAnch;

    @FXML
    private AnchorPane damAnch;

    @FXML
    private AnchorPane empAnch;

    @FXML
    private AnchorPane memAnch;

    @FXML
    private AnchorPane payAnch;

    @FXML
    private AnchorPane sectAnch;

    @FXML
    private AnchorPane supAnch;

    @FXML
    private Label lblWellcome;

    @FXML
    public Label userGmail;

    @FXML
    private Label lblLogout;

    @FXML
    public AnchorPane dashboardBody;

    @FXML
    private AnchorPane workSpace;

    @FXML
    private AnchorPane body;




    //ADD USER --------
    @FXML
    private Button btnAddNewUser;

    @FXML
    void addNewUser(ActionEvent event) {
        try{
            Parent load = FXMLLoader.load(getClass().getResource("/view/CreateAccount.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.setTitle("Create Account");
            stage.show();
        }catch (IOException e1){
            System.out.println("Unable to open create Account page");
            e1.printStackTrace();
        }
    }
    //------



    //SIDE NAME BAR SHOWINGS -----------
    @FXML
    void navigateToWhere(MouseEvent event) {
        btnNamesAnchor.setVisible(true);
    }

    @FXML
    void navigateExit(MouseEvent event) {
        btnNamesAnchor.setVisible(false);
    }
    //---------




    //BOOK MANAGE -------
    @FXML
    void showName(MouseEvent event) {
        bookAnch.setVisible(true);
    }

    @FXML
    void closeName(MouseEvent event) {
        bookAnch.setVisible(false);
    }

    @FXML
    void openManageBookView(MouseEvent event) {
        try{
            body.getChildren().clear();
            AnchorPane loard = FXMLLoader.load(getClass().getResource("/view/ManageBooksView.fxml"));
            body.getChildren().add(loard);
        }catch (IOException e1){
            System.out.println("IoExeption");
            e1.printStackTrace();
        }
    }
    //----




    //MEMBER
    @FXML
    void closeNameMem(MouseEvent event) {
        memAnch.setVisible(false);
    }

    @FXML
    void showNameMem(MouseEvent event) {
        memAnch.setVisible(true);
    }

    @FXML
    void OpenMamageMemberView(MouseEvent event) {
        try{
            body.getChildren().clear();
            AnchorPane loard = FXMLLoader.load(getClass().getResource("/view/ManageMembersView.fxml"));
            body.getChildren().add(loard);
        }catch (IOException e1){
            System.out.println("IoExeption");
            e1.printStackTrace();
        }
    }



    //AUTHORS
    @FXML
    void showNameAuth(MouseEvent event) {
        authorAnch.setVisible(true);
    }


    @FXML
    void closeNameAuth(MouseEvent event) {
        authorAnch.setVisible(false);
    }

    @FXML
    void OpenMamageAuthorView(MouseEvent event) {
        try{
            body.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/ManageAuthorView.fxml"));
            body.getChildren().add(load);
        }catch (IOException e1){
            System.out.println("IOException \nUnable to load Manage Author View");
            e1.printStackTrace();
        }
    }





    //SECTIONS
    @FXML
    void showNameSec(MouseEvent event) {
        sectAnch.setVisible(true);
    }

    @FXML
    void closeNameSec(MouseEvent event) {
        sectAnch.setVisible(false);
    }




    //SUPPLIERS
    @FXML
    void showNameSup(MouseEvent event) {
        supAnch.setVisible(true);
    }


    @FXML
    void closeNameSup(MouseEvent event) {
        supAnch.setVisible(false);
    }




    //CUPBOARD
    @FXML
    void showNameCup(MouseEvent event) {
        cupAnch.setVisible(true);
    }


    @FXML
    void closeNameCup(MouseEvent event) {
        cupAnch.setVisible(false);
    }




    //PAYMENTS
    @FXML
    void showNamePay(MouseEvent event) {
        payAnch.setVisible(true);
    }


    @FXML
    void closeNamePay(MouseEvent event) {
        payAnch.setVisible(false);
    }




    //DAMAGE
    @FXML
    void showNameDama(MouseEvent event) {
        damAnch.setVisible(true);
    }


    @FXML
    void closeNameDama(MouseEvent event) {
        damAnch.setVisible(false);
    }



    //EMPLOYEE
    @FXML
    void showNameEmp(MouseEvent event) {
        empAnch.setVisible(true);
    }

    @FXML
    void closeNameEmp(MouseEvent event) {
        empAnch.setVisible(false);
    }



    //LOG OUT
    @FXML
    void loardLoginPage(MouseEvent event) {
        logout();
    }

    public void logout(){
        try{
            dashboardBody.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            dashboardBody.getChildren().add(load);
            Stage stage = (Stage) dashboardBody.getScene().getWindow();
            stage.setTitle("Log in");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserDetailsAndSetGmail();
        openHomePage();
    }



    public void getUserDetailsAndSetGmail(){
        try{
            if(DashBoardContro.isAdminLogin()){
                //DTO IS NULL ...(admin has log in)
                userGmail.setText("admin");
                btnAddNewUser.setVisible(true);
            }else{
                //DTO IS NOT NULL... (user has login)
                dto = userModel.getUserDetails(userEmail);
                userGmail.setText(dto.getEmail());
                btnAddNewUser.setVisible(false);
            }

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
    private void openHomePage(){
        try{
            body.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage.fxml"));
            AnchorPane load = fxmlLoader.load();

            HomePage homepageContro = fxmlLoader.getController();
            homepageContro.setUserDetails(userEmail);

            body.getChildren().add(load);
        }catch(Exception e){
            System.out.println("Unable to loard DashBoard on Dashboard conto");
            e.printStackTrace();
        }
    }
}