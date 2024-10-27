package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.db.DBConnection;
import edu.ijse.gdse.libarymanagementsystem.dto.UserDto;
import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class LogInModel {
    public boolean checkEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user WHERE email = ?";
        ResultSet res = CrudUtil.execute(sql,email);
        return res.next()? true : false;
    }

    public boolean checkPassword(String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "select * from user where email = ? and password = ?";
        ResultSet res = CrudUtil.execute(sql,email,password);
        return res.next()? true : false;
    }
}
