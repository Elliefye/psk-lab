package lt.vu.persistence;

import lt.vu.entities.Post;
import lt.vu.entities.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Alternative
@ApplicationScoped
public class TravelPostDAO extends PostDAO implements IPostDAO {

    @Inject
    private TagDAO tagDAO;

    @Override
    public void persist(Post post) {
        List <Tag> tags = post.getTags();
        tags.add(tagDAO.findOne(2));
        post.setTags(tags);
        System.out.println("Persisted a travel post.");
        super.persist(post);
    }
}
