package lt.vu.persistence;

import lt.vu.entities.Author;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Specializes
@ApplicationScoped
public class AuthorDAOType extends AuthorDAO {
    @PostConstruct
    private void LogAuthorType(Author author) {
        if (author.getDateJoined().before(Date.valueOf(LocalDate.of(2020, 1, 1)))) {
            System.out.println("Author " + author.getUsername() + " is an legacy user.");
        }
        else {
            System.out.println("Author " + author.getUsername() + " is an new user.");
        }
    }

    @Override
    public void persist(Author author){
        super.persist(author);
        LogAuthorType(author);
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = super.getAll();

        for(Author a: authors) {
            LogAuthorType(a);
        }
        return authors;
    }

    @Override
    public Author getById(int id){
        Author author = super.getById(id);
        LogAuthorType(author);
        return author;
    }
}