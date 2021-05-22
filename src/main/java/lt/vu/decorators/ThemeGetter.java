package lt.vu.decorators;

import lt.vu.entities.Author;
import lt.vu.entities.Post;
import lt.vu.entities.Tag;

import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.*;

@RequestScoped
public class ThemeGetter implements IThemes, Serializable {
    public String getCommonTheme(Author author) {
        if (author.getPosts().isEmpty()) {
            return author.getUsername() + " doesn't have any posts.";
        }

        HashMap<String, Integer> tags = new HashMap<String, Integer>();

        for (Post post : author.getPosts()) {
            for (Tag tag : post.getTags()) {
                int count = tags.getOrDefault(tag.getName(), 0);
                tags.put(tag.getName(), count + 1);
            }
        }

        Optional<Map.Entry<String, Integer>> mostCommon = tags.entrySet()
                .stream()
                .max((Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) ->
                        e1.getValue().compareTo(e2.getValue())
                );

        return mostCommon.map(Map.Entry::getKey).orElse(author.getUsername() + " doesn't have any posts.");
    }
}
