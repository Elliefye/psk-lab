package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;

import lt.vu.entities.Author;
import lt.vu.entities.Post;
import lt.vu.entities.Tag;
import lt.vu.persistence.AuthorDAO;
import lt.vu.persistence.PostDAO;
import lt.vu.persistence.TagDAO;

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
public class Tags implements Serializable {
    @Inject
    private TagDAO tagDAO;

    @Getter
    private List<Tag> allTags;

    @Getter @Setter
    private Tag tagToCreate = new Tag();

    @PostConstruct
    public void init(){
        loadAllTags();
    }

    private void loadAllTags(){
        this.allTags = tagDAO.getAll();
    }

    @Transactional
    public String createTag() {
        tagDAO.persist(tagToCreate);
        return "tags?faces-redirect=true";
    }
}
