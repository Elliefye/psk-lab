package lt.vu.persistence;

import lt.vu.entities.Post;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class PostDAO implements IPostDAO {
    @Inject
    private EntityManager em;

    public void persist(Post post){ this.em.persist(post); }

    public Post getById(int id){
        return em.find(Post.class, id);
    }

    public Post update(Post post){
        return em.merge(post);
    }

    public List<Post> loadAll() {
        return em.createNamedQuery("Post.getAll", Post.class).getResultList();
    }
}
