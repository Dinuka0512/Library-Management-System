package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BranchModel {
    public ArrayList<String> getAllBranchId() throws SQLException, ClassNotFoundException {
        String sql = "select Branch_Id from Branch";
        ResultSet res = CrudUtil.execute(sql);
        ArrayList<String> ids = new ArrayList<>();

        while(res.next()){
            ids.add(res.getString("Branch_Id"));
        }

        return ids;
    }

    public String getSelectedBranchName(String branchId){
        try{
            String sql = "select name from Branch where Branch_Id = ?";
            ResultSet res = CrudUtil.execute(sql,branchId);
            if(res.next()){
                return res.getString("name");
            }
        }catch (ClassNotFoundException e1){
            System.out.println("Class not found Exception");
            e1.printStackTrace();
        }catch (SQLException e2){
            System.out.println("SQL Exception");
            e2.printStackTrace();
        }
        return null;
    }
}
