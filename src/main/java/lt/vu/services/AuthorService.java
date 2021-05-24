package lt.vu.services;

import lt.vu.entities.Author;
import lt.vu.persistence.AuthorDAO;
import lt.vu.rest.AuthorDTO;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

public class AuthorService {
    @Inject
    AuthorDAO authorDAO;

    public Author get(int id) throws NotFoundException {
        Author author = authorDAO.getById(id);
        if (author == null) {
            throw new NotFoundException("Author with Id " + id + " not found");
        }
        return author;
    }

    public void updateFromDTO(AuthorDTO authorDTO) {
        Author author = authorDAO.getById(authorDTO.getId());
        if (author == null) {
            throw new NotFoundException("Author with Id " + authorDTO.getId() + " not found");
        }
        author.setUsername(authorDTO.getUsername());
        author.setDateJoined(authorDTO.getDateJoined());
        authorDAO.update(author);
    }

    public void saveFromDTO(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setUsername(authorDTO.getUsername());
        author.setDateJoined(authorDTO.getDateJoined());
        authorDAO.persist(author);
    }
}
