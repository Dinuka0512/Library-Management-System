package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.dto.BookDto;
import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookIssueModel {
    public ArrayList<String> getPopularBooks() throws SQLException, ClassNotFoundException {
        String sql = "select *, count(book_Id) from book_Issue group by book_Id limit 4";
        ResultSet res = CrudUtil.execute(sql);
        ArrayList<String> dtos = new ArrayList<>();
        while(res.next()){
            dtos.add(res.getString("Book_Id"));
        }

        return dtos;
    }
}
