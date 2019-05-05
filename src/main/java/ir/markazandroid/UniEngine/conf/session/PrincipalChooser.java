package ir.markazandroid.UniEngine.conf.session;

/**
 * Created by Ali on 4/7/2019.
 */
public interface PrincipalChooser <P> {

    boolean accept(P principal);
}
