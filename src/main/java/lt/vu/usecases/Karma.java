package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Author;
import lt.vu.persistence.AuthorDAO;
import lt.vu.services.KarmaCalculator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class Karma implements Serializable {
    @Inject
    AuthorDAO authorDAO;

    @Inject
    KarmaCalculator karmaCalculator;

    private CompletableFuture<Integer> karmaCalculation = null;

    @Getter
    @Setter
    private int authorId;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        authorId = Integer.parseInt(requestParameters.get("id"));
    }

    public String calculateKarma() {
        Author author = authorDAO.getById(this.authorId);
        karmaCalculation = CompletableFuture.supplyAsync(() -> karmaCalculator.CalculateKarma(author));
        return "author?faces-redirect=true&id=" + this.authorId;
    }

    public boolean isCalculatingKarma() {
        return karmaCalculation != null && !karmaCalculation.isDone();
    }

    public String karmaCalculatorStatus() throws ExecutionException, InterruptedException {
        if (karmaCalculation == null) {
            return null;
        } else if (isCalculatingKarma()) {
            return "Calculating karma...";
        }
        return "Karma for user " + authorDAO.getById(this.authorId).getUsername() + " is " + karmaCalculation.get();
    }
}
