package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Author;
import lt.vu.services.AuthorService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/authors")
public class AuthorController {
    @Inject
    @Setter
    @Getter
    private AuthorService authorService;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        try {
            Author author = authorService.get(id);
            AuthorDTO authorDTO = new AuthorDTO(author.getId(), author.getUsername(), author.getDateJoined());
            return Response.ok(authorDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(AuthorDTO authorDTO) {
        try {
            authorService.updateFromDTO(authorDTO);
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
    public Response create(AuthorDTO authorDTO) {
        try {
            authorService.saveFromDTO(authorDTO);
            return Response.ok().build();
        }
        catch (OptimisticLockException | EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
