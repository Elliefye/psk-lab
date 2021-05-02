package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.model.Author;
import lt.vu.mybatis.dao.AuthorMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Model
public class AuthorsMyBatis implements Serializable{
    @Inject
    private AuthorMapper authorMapper;

    @Getter
    private List<Author> allAuthors;

    @Getter @Setter
    private Author authorToCreate = new Author();

    @PostConstruct
    public void init(){
        this.allAuthors = authorMapper.selectAll();
    }

    @Transactional
    public String createAuthor(){
        authorMapper.insert(authorToCreate);
        return "/myBatis/authors?faces-redirect=true";
    }
}
