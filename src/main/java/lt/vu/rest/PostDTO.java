package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class PostDTO {

    private Integer id;
    private String title;
    private String body;
    private Date date;
    private AuthorDTO author;

    public PostDTO(Integer id, String title, String body, Date date, AuthorDTO author) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.author = author;
    }
}
