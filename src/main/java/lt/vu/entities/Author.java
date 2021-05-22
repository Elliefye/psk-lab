package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
@NamedQueries({
        @NamedQuery(name = "Author.getAll", query = "select a from Author as a")
})
public class Author implements Serializable {
    public Author() {
        this.dateJoined = Date.valueOf(LocalDate.now());
    }
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private int id;

    @Basic
    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Basic
    @Column(name = "DATE_JOINED", nullable = true)
    private Date dateJoined;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    @OneToMany(mappedBy="author")
    private List<Post> posts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(username, author.username) && Objects.equals(dateJoined, author.dateJoined);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, dateJoined);
    }
}
