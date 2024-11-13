package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookIssueModel {
    public String getNextIssueId() throws SQLException, ClassNotFoundException{
        String sql = "select Issue_Id from book_Issue order by Issue_Id desc limit 1";
        ResultSet res = CrudUtil.execute(sql);
        if(res.next()){
            String lastId = res.getString("Issue_Id"); //B001
            String subString = lastId.substring(1);  //001
            int i = Integer.parseInt(subString); //1
            int newIndex = i + 1; //2
            return String.format("I%03d",newIndex); //B002
        }

        return "I001";
    }

}
