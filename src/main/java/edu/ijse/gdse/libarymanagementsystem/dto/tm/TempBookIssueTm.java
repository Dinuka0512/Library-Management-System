package edu.ijse.gdse.libarymanagementsystem.dto.tm;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TempBookIssueTm {
    private String issueId;
    private String bookId;
    private String name;
    private int qty;
}
