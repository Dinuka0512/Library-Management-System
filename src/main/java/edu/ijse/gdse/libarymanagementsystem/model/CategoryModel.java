package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryModel {
    public ArrayList<String> getAllCategoryIds(){
        try{
            String sql = "select Category_Id from Category";
            ResultSet res = CrudUtil.execute(sql);

            ArrayList<String> dto =new ArrayList<>();
            while(res.next()){
                String name = res.getString("Category_Id");
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
