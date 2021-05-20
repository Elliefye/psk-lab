package lt.vu.persistence;

import lt.vu.entities.Post;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.List;
import java.util.stream.Collectors;

@Alternative
@ApplicationScoped
public class TravelPostDAO extends PostDAO implements IPostDAO {

    @Override
    public List<Post> loadAll() {
        return super.loadAll().stream()
                .filter(p -> p.getTags().stream().anyMatch(t -> t.getName().equals("travel")))
                .collect(Collectors.toList());
    }
}
