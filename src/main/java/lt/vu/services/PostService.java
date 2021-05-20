package lt.vu.services;

import lt.vu.entities.Post;
import lt.vu.persistence.AuthorDAO;
import lt.vu.persistence.PostDAO;
import lt.vu.rest.PostDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class PostService {
    @Inject
    PostDAO postDAO;
    @Inject
    AuthorDAO authorDAO;

    public Post get(int id) throws NotFoundException {
        Post post = postDAO.getById(id);
        if (post == null) {
            throw new NotFoundException("Post with Id " + id + " not found");
        }
        return post;
    }

    public void updateFromDTO(PostDTO postDTO) {
        Post post = postDAO.getById(postDTO.getId());
        if (post == null) {
            throw new NotFoundException("Post with Id " + postDTO.getId() + " not found");
        }
        post.setTitle(postDTO.getTitle());
        post.setBody(postDTO.getBody());
        post.setDate(postDTO.getDate());
        post.setAuthor(authorDAO.getById(postDTO.getAuthor().getId()));
        postDAO.update(post);
    }

    public void saveFromDTO(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setBody(postDTO.getBody());
        post.setDate(postDTO.getDate());
        post.setAuthor(authorDAO.getById(postDTO.getAuthor().getId()));
        postDAO.persist(post);
    }
}
