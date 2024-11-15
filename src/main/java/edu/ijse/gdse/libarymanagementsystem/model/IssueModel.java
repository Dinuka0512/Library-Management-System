package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.dto.IssueTableDto;
import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IssueModel {
    public String getNextIssueId() throws SQLException, ClassNotFoundException{
        String sql = "select Issue_Id from Issue order by Issue_Id desc limit 1";
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

    public ArrayList<IssueTableDto> getAllData() throws SQLException, ClassNotFoundException{
        String sql = "select * from Issue";
        ResultSet res = CrudUtil.execute(sql);
        ArrayList<IssueTableDto> dtos = new ArrayList<>();
        while(res.next()){
            IssueTableDto dto =new IssueTableDto(
                    res.getString("Issue_Id"),
                    res.getString("Member_Id"),
                    res.getString("User_Id"),
                    res.getString("Date"),
                    res.getString("Time"),
                    res.getBoolean("isCompleted")
            );

            dtos.add(dto);
        }
        return dtos;
    }

}
