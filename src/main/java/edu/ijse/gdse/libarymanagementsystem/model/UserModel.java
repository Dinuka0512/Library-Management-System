package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.dto.UserDto;
import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public boolean updateUser(String userName, String password,String email) throws SQLException, ClassNotFoundException{
        String sql = "UPDATE user SET name = ? , password = ? WHERE email = ?";
        boolean res = CrudUtil.execute(sql,userName,password,email);
        return res;
    }

    public UserDto getUserDetails(String userEmail) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user WHERE email = ?";

        ResultSet res = CrudUtil.execute(sql,userEmail);
        res.next();

        UserDto dto = new UserDto(
                res.getString("User_Id"),
                res.getString("name"),
                res.getString("address"),
                res.getString("password"),
                res.getString("email")
        );

        return dto;
    }
}
