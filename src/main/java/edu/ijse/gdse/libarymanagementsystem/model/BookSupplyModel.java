package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.dto.BookSuplyDto;
import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookSupplyModel {
    public BookSuplyDto getAll(String bookId) throws ClassNotFoundException, SQLException {
        String sql = "select * from book_Supply where Book_Id = ?";
        ResultSet res = CrudUtil.execute(
                sql,
                bookId
        );

        if(res.next()){
            BookSuplyDto dto = new BookSuplyDto(
                    res.getString("Book_Id"),
                    res.getString("Supplier_Id"),
                    res.getInt("qty")
            );

            return dto;
        }
        return null;
    }
}
