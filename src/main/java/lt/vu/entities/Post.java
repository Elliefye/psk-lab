package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
@NamedQueries({
        @NamedQuery(name = "Post.getAll", query = "select p from Post as p")
})
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private int id;

    @Basic
    @Column(name = "TITLE", nullable = false, length = 100)
    private String title;

    @Basic
    @Column(name = "BODY", nullable = false, length = 255)
    private String body;

    @Basic
    @Column(name = "DATE", nullable = true)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID", nullable = false)
    private Author author;

    @ManyToMany
    @JoinTable(
            name = "POST_TAG",
            joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    private List<Tag> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && Objects.equals(title, post.title) && Objects.equals(body, post.body) && Objects.equals(date, post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, date);
    }
}
