package edu.ijse.gdse.libarymanagementsystem.model;
import edu.ijse.gdse.libarymanagementsystem.dto.BookDto;
import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;
import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class BookModel {
    public ArrayList<BookDto> getAllBooks() throws SQLException, ClassNotFoundException{
        String sql = "select * from Book";
        ResultSet res = CrudUtil.execute(sql);
        ArrayList<BookDto> dtos =  new ArrayList<>();
        while(res.next()){
            BookDto dto = new BookDto(
                    res.getString("Book_Id"),
                    res.getString("name"),
                    res.getInt("qty"),
                    res.getDouble("price")
            );

            dtos.add(dto);
        }
        return dtos;
    }

    public String getNextBookId() throws SQLException, ClassNotFoundException{
        String sql = "select Book_Id from Book Order by Book_Id desc limit 1";
        ResultSet res = CrudUtil.execute(sql);
        if(res.next()){
            String lastId = res.getString("Book_Id"); //B001
            String subString = lastId.substring(1);  //001
            int i = Integer.parseInt(subString); //1
            int newIndex = i + 1; //2
            return String.format("B%03d",newIndex); //B002
        }

        return "B001";
    }
}
