package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorModel {
    public ArrayList<String> getAllAuthorIds(){
        try{
            String sql = "select Author_Id from Author";
            ResultSet res = CrudUtil.execute(sql);

            ArrayList<String> dto =new ArrayList<>();
            while(res.next()){
                String name = res.getString("Author_Id");
                dto.add(name);
            }
            return dto;

        }catch (SQLException e1){
            System.out.println("SQL Exception");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("Class Not Found");
            e2.printStackTrace();
        }

        return null;
    }
}
