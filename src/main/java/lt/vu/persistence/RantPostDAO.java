package lt.vu.persistence;

import lt.vu.entities.Post;
import lt.vu.entities.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Alternative
@ApplicationScoped
public class RantPostDAO extends PostDAO implements IPostDAO {

    @Inject
    private TagDAO tagDAO;

    @Override
    public void persist(Post post) {
        List <Tag> tags = post.getTags();
        tags.add(tagDAO.findOne(3));
        post.setTags(tags);
        System.out.println("Persisted a ramble post.");
        super.persist(post);
    }
}
