package ir.markazandroid.UniEngine.JSONParser;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * Created by Ali on 4/24/2019.
 */
public class ParserInitializr {

    private final String PARSER_INITIALIZR_TAG="ParserInitializr: ";

    private Parser parser;

    public ParserInitializr(Parser parser) {
        this.parser = parser;
    }

    public void findAnnotatedClasses() {
        System.out.println(PARSER_INITIALIZR_TAG+"Finding JSON class candidates");
        ClassPathScanningCandidateComponentProvider provider = createComponentScanner();
        for (BeanDefinition beanDef : provider.findCandidateComponents("ir.markazandroid.UniEngine")) {
            addClass(beanDef);
        }
    }

    private ClassPathScanningCandidateComponentProvider createComponentScanner() {
        // Don't pull default filters (@Component, etc.):
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(JSON.class));
        return provider;
    }

    private void addClass(BeanDefinition beanDef) {
        try {
            Class<?> cl = Class.forName(beanDef.getBeanClassName());
            if (cl.isAnnotationPresent(JSON.class)){
                parser.addWithSuperClasses(cl);
                System.out.println(PARSER_INITIALIZR_TAG+"Added class= "+cl.getName());
            }
        } catch (Exception e) {
            System.err.println("Got exception: " + e.getMessage());
        }
    }
}
