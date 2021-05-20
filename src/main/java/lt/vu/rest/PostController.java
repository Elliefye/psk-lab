package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;
import lt.vu.services.PostService;
import lt.vu.entities.Post;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/posts")
public class PostController {

    @Inject
    @Setter @Getter
    private PostService postService;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        try {
            Post post = postService.get(id);
            PostDTO postDTO = new PostDTO(
                    post.getId(),
                    post.getTitle(),
                    post.getBody(),
                    post.getDate(),
                    new AuthorDTO(post.getAuthor().getId(), post.getAuthor().getUsername(), post.getAuthor().getDateJoined()));
            return Response.ok(postDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(PostDTO postDTO) {
        try {
            postService.updateFromDTO(postDTO);
            return Response.ok().build();
        }
        catch (OptimisticLockException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(PostDTO postDTO) {
        try {
            postService.saveFromDTO(postDTO);
            return Response.ok().build();
        }
        catch (OptimisticLockException | EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}