package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SectionModel {
    public ArrayList<String> getAllSectionIds() {
        try {
            String sql = "select section_Id from section";
            ResultSet res = CrudUtil.execute(sql);
            ArrayList<String> dto = new ArrayList<>();
            while (res.next()) {
                dto.add(res.getString("section_Id"));
            }
            return dto;

        }catch (SQLException e1){
            System.out.println("SQL EXCEPTION");
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("Class Not found Exception");
            e2.printStackTrace();
        }
        return null;
    }

    public String getSectionName(String sectionId) throws SQLException, ClassNotFoundException{
        String sql = "select name from section where Section_Id = ?";
        ResultSet res = CrudUtil.execute(sql,sectionId);
        if(res.next()){
            return res.getString("name");
        }
        return null;
    }
}
