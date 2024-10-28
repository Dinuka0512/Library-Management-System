package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.db.DBConnection;
import edu.ijse.gdse.libarymanagementsystem.dto.AuthorDto;
import edu.ijse.gdse.libarymanagementsystem.dto.BookDto;
import edu.ijse.gdse.libarymanagementsystem.dto.CategoryDto;
import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class ManabeBooksViewModel {
    public String saveNewBook(BookDto bookDto, String authorId, String categoryId) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        //TRANSACTION PART
        try{
            con.setAutoCommit(false);
            String bookSql = "insert into Book values (?,?,?,?)";
            boolean bookRes = CrudUtil.execute(
                    bookSql,
                    bookDto.getBookId(),
                    bookDto.getName(),
                    bookDto.getQty(),
                    bookDto.getPrice()
            );

            boolean isBookSaved = bookRes;
            if(isBookSaved){
                boolean isCategorySaved = true;

                String categorySql = "insert into Book_Category values (?,?)";
                boolean categoryRes = CrudUtil.execute(
                  categorySql,
                  bookDto.getBookId(),
                  categoryId
                );

                if(!categoryRes){
                    isCategorySaved = false;
                }

                if(isCategorySaved){
                    boolean isAuthorSaved = true;
                    String authorSql = "insert into Author_Book values (?,?)";
                    boolean authorRes = CrudUtil.execute(
                            authorSql,
                            bookDto.getBookId(),
                            authorId
                    );

                    if(!authorRes){
                        isAuthorSaved = false;
                    }
                    if(isAuthorSaved){
                        con.commit();
                        return "saved Successfully";
                    }else{
                        con.rollback();
                        return "Suthor saving error";
                    }
                }else{
                    con.rollback();
                    return "Category saving error";
                }
            }else{
                con.rollback();
                return "Book saving error";
            }
        }catch(Exception e){
            con.rollback();
            throw e;
        }finally {
            con.setAutoCommit(true);
        }
    }
}
