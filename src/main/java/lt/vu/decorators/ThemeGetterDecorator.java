package lt.vu.decorators;

import lt.vu.entities.Author;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public class ThemeGetterDecorator implements IThemes{
    @Inject
    @Delegate
    @Any
    IThemes themeGetter;

    public String getCommonTheme(Author author) {
        String mostCommon = themeGetter.getCommonTheme(author);

        if(mostCommon.equals("Travel")) {
            System.out.println(author.getUsername() + " has posts about travel.");
        }

        return mostCommon;
    }
}
