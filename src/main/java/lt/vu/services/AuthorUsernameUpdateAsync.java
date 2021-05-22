package lt.vu.services;

import lt.vu.entities.Author;
import lt.vu.entities.Post;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class AuthorUsernameUpdateAsync {
    @PersistenceContext
    @Async
    private EntityManager em;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public String update(Integer id, String username, long preWait, long postWait) {
        try {
            Thread.sleep(preWait);
            Author author = em.find(Author.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            Thread.sleep(postWait);
            author.setUsername(username);
            em.flush();
        }
        catch (OptimisticLockException e) {
            System.out.println("OptimisticLockException in AuthorUsernameUpdateAsync");
            return "OptimisticLockException";
        } catch (InterruptedException ignored) {}
        return "Updated";
    }
}
