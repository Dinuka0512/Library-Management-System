package edu.ijse.gdse.libarymanagementsystem.model;

import edu.ijse.gdse.libarymanagementsystem.dto.AuthorDto;
import edu.ijse.gdse.libarymanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorModel {
    public String genarateAuthorId() throws  SQLException, ClassNotFoundException{
        String sql = "select Author_Id from Author Order by Author_Id desc limit 1";
        ResultSet res = CrudUtil.execute(sql);
        if(res.next()){
            String lastId = res.getString("Author_Id"); //A001
            String subString = lastId.substring(1);

            /*
            *  ------->   A001
            *  0 index -> A
            *  1 index -> 0
            *  2 index -> 0
            *  3 index -> 1
            *
            * -----> lastId.subString(1);
            *       (That means Starting at ist Index)
            *       So the output is
            *       001
            * */
            int i = Integer.parseInt(subString);
            /*
            * CONVERT THE STRING TO INTEGER
            * ==============================
            *
            * (String) 001 ----> Convert To Integer
            * 001 ---(AFTER CONVERT)--> 1
            * */
            i = i +1; // ADD
            String newId = String.format("A%03d",i);

            /*
            * "A%03d"
            *
            * --> A - Character
            * --> %d - formats the number as a decimal integer.
            * --> 0 - specifies that the integer should be padded
            * --> 3 -  if i has fewer than 3 digits, it will be padded with leading zeros.
            * */
            return newId;
        }
        return "A001";
    }

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

    public String getAuthorName(String authorId) throws SQLException,ClassNotFoundException{
        String sql = "select * from Author where Author_Id = ?";
        ResultSet res = CrudUtil.execute(sql,authorId);
        if(res.next()){
            String name = res.getString("name");
            return name;
        }
        return null;
    }

    public boolean saveNewAuthor(AuthorDto dto) throws SQLException, ClassNotFoundException{
        String sql = "insert into Author values (?,?,?,?,?)";
        boolean res = CrudUtil.execute(
                sql,
                dto.getAuhorId(),
                dto.getName(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getContract()
        );

        return res;
    }

    public String getAuthorIds(String name) throws  ClassNotFoundException, SQLException{
        String sql = "select Author_Id from Author where  name = ?";
        ResultSet res = CrudUtil.execute(sql, name);
        if(res.next()){
            return  res.getString("Author_Id");
        }
        return null;
    }
}
