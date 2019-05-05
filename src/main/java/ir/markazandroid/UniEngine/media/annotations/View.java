package ir.markazandroid.UniEngine.media.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Ali on 4/22/2019.
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface View {

    String value();

}
