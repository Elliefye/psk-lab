package lt.vu.services;

import lt.vu.entities.Post;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class PostTitleUpdateAsync {
    @PersistenceContext
    @Async
    private EntityManager em;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public String update(Integer id, String title, long preWait, long postWait) {
        try {
            Thread.sleep(preWait);
            Post post = em.find(Post.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            Thread.sleep(postWait);
            post.setTitle(title);
            em.flush();
        }
        catch (OptimisticLockException e) {
            System.out.println("OptimisticLockException in CarModelUpdaterAsync");
            return "OptimisticLockException";
        } catch (InterruptedException ignored) {}
        return "Updated";
    }
}
