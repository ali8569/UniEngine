package ir.markazandroid.UniEngine.media.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Ali on 4/22/2019.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface XmlAttribute {
    String value();
}
