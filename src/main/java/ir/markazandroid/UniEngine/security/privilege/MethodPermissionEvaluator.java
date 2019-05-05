package ir.markazandroid.UniEngine.security.privilege;

import ir.markazandroid.UniEngine.persistance.entity.PrivilegeEntity;
import javafx.print.Collation;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Ali on 4/14/2018.
 */

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Component
public class MethodPermissionEvaluator implements PermissionEvaluator {

    public static final String PERMISSION_WRITE_STORAGE="writeStorage";
    public static final String PERMISSION_READ_STORAGE="readStorage";

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String method = (String) permission;
        switch (method){
            case PERMISSION_WRITE_STORAGE:
                return hasStorageWritePermission(authentication,targetDomainObject);

            case PERMISSION_READ_STORAGE:
                return hasStorageReadPermission(authentication,targetDomainObject);

            default:return false;

        }
    }

    private boolean hasStorageWritePermission(Authentication authentication, Object targetDomainObject) {
        String path = (String) targetDomainObject;
        return hasPrefixPermission(authentication.getAuthorities(),
                PrivilegeEntity.STORAGE_WRITE_PREFIX
                ,path);
    }

    private boolean hasStorageReadPermission(Authentication authentication, Object targetDomainObject) {
        String path = (String) targetDomainObject;
        return hasPrefixPermission(authentication.getAuthorities(),
                PrivilegeEntity.STORAGE_READ_PREFIX
                ,path)
                ||
                hasPrefixPermission(authentication.getAuthorities(),
                PrivilegeEntity.STORAGE_WRITE_PREFIX
                ,path);
    }


    private static boolean hasPrefixPermission(Collection<? extends GrantedAuthority> authorities,String permissionPrefix,String path){
        return authorities.stream()
                .map((Function<GrantedAuthority, String>) GrantedAuthority::getAuthority)
                .anyMatch(s -> {
                    if (s==null) return false;
                    if (s.startsWith(permissionPrefix)){
                        String pattern = s.substring(permissionPrefix.length());
                        AntPathMatcher matcher = new AntPathMatcher();
                        System.out.println("pattern="+pattern+" path="+path);
                        return matcher.matchStart(pattern,path);
                    }
                    return false;
                });

    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new RuntimeException();
    }
}
