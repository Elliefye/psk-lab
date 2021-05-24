package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;

import lt.vu.decorators.IThemes;
import lt.vu.entities.Author;
import lt.vu.entities.Post;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.AuthorDAO;
import lt.vu.persistence.IPostDAO;
import lt.vu.persistence.PostDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Model
public class AuthorPosts implements Serializable {
    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private IPostDAO postDAO;

    @Inject
    private IThemes themeGetter;

    @Getter
    private Author author;

    @Getter @Setter
    private Post postToCreate = new Post();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int authorId = Integer.parseInt(requestParameters.get("id"));
        this.author = authorDAO.getById(authorId);
        System.out.println(themeGetter.getCommonTheme(author));
    }

    @LoggedInvocation
    @Transactional
    public String createPost() {
        postToCreate.setAuthor(this.author);
        postToCreate.setDate(Date.valueOf(LocalDate.now()));
        postDAO.persist(postToCreate);
        postToCreate = new Post();
        return "author?faces-redirect=true&id=" + author.getId();
    }

    public List<Post> getAllPosts() {
        return postDAO.loadAll();
    }
}
