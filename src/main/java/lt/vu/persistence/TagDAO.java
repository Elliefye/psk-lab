package lt.vu.persistence;

import lt.vu.entities.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class TagDAO {
    @Inject
    private EntityManager em;

    public void persist(Tag tag){
        this.em.persist(tag);
    }

    public Tag findOne(Integer id){
        return em.find(Tag.class, id);
    }

    public Tag update(Tag tag){
        return em.merge(tag);
    }

    public List<Tag> getAll() {
        return em.createNamedQuery("Tag.getAll", Tag.class).getResultList();
    }
}
