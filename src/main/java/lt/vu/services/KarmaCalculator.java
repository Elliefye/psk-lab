package lt.vu.services;

import lt.vu.entities.Author;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@ApplicationScoped
public class KarmaCalculator {
    public int CalculateKarma(Author author) {
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException ignored) {}
        int karma = author.getPosts().size() + new Random().nextInt(10);
        System.out.println("Karma for " + author.getUsername() + " is " + karma);
        return karma;
    }
}
