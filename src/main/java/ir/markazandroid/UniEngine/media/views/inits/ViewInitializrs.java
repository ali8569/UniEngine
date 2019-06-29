package ir.markazandroid.UniEngine.media.views.inits;

import ir.markazandroid.UniEngine.media.views.BasicView;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Ali on 6/8/2019.
 */

@Component
public class ViewInitializrs {

    private final ApplicationContext context;

    @Autowired
    public ViewInitializrs(ApplicationContext context) {
        this.context = context;
    }

    public ViewInitializr getViewInitializrForView(BasicView basicView) {
        try {
            return (ViewInitializr) context.getBean(basicView.getType() + "_initializr");
        } catch (NoSuchBeanDefinitionException ignored) {
            return null;
        }
    }
}
