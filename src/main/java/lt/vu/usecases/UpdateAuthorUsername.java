package lt.vu.usecases;

import lt.vu.services.AuthorUsernameUpdateAsync;
import lt.vu.services.PostTitleUpdateAsync;

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
public class UpdateAuthorUsername implements Serializable {
    private int authorId;

    @Inject
    private AuthorUsernameUpdateAsync usernameUpdater;

    private CompletableFuture<String> update1 = null;

    private CompletableFuture<String> update2 = null;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        authorId = Integer.parseInt(requestParameters.get("id"));
    }

    public String test(){
        update1 = CompletableFuture.supplyAsync(() -> usernameUpdater.update(authorId,"NewUsername", 0, 1000));
        update2 = CompletableFuture.supplyAsync(() -> usernameUpdater.update(authorId,"AnotherUsername", 200, 100));
        return "author?id=" + this.authorId + "&faces-redirect=true";
    }

    public boolean isUpdate1Running() {
        return this.update1 != null && !this.update1.isDone();
    }

    public boolean isUpdate2Running() {
        return this.update2 != null && !this.update2.isDone();
    }

    public String getUpdate1Status() throws ExecutionException, InterruptedException {
        if (update1 == null) {
            return null;
        } else if (isUpdate1Running()) {
            return "Update 1 is in progress...";
        }
        return "Update1 Status: " + update1.get();
    }

    public String getUpdate2Status() throws ExecutionException, InterruptedException {
        if (update2 == null) {
            return null;
        } else if (isUpdate2Running()) {
            return "Update 2 is in progress...";
        }
        return "Update2 Status: " + update2.get();
    }
}
