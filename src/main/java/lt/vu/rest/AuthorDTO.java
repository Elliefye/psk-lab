package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter

public class AuthorDTO {
    private Integer id;
    private String username;
    private Date dateJoined;

    public AuthorDTO(){}

    public AuthorDTO(Integer id, String username, Date dateJoined) {
        this.id = id;
        this.username = username;
        this.dateJoined = dateJoined;
    }
}
