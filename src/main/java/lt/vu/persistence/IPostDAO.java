package lt.vu.persistence;

import lt.vu.entities.Post;

import java.util.List;

public interface IPostDAO {
    void persist(Post post);
    Post getById(int id);
    Post update(Post post);
    List<Post> loadAll();
}
