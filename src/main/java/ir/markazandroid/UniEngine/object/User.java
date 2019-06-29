package ir.markazandroid.UniEngine.object;

import ir.markazandroid.UniEngine.persistance.entity.PrivilegeEntity;
import ir.markazandroid.UniEngine.persistance.entity.RoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Ali on 6/10/2019.
 */
public abstract class User implements UserDetails, PrivateStorageOwner {

    protected String username;
    protected String password;
    private ArrayList<? extends GrantedAuthority> authorities;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public void makePrivileges() {
        ArrayList<GrantedAuthority> authorities = getRole() != null ? getRole().getAllAuthorities() : new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(PrivilegeEntity.STORAGE_WRITE_PREFIX + getStoragePrefix() + "/**"));
        authorities.add(new SimpleGrantedAuthority(PrivilegeEntity.STORAGE_READ_PREFIX + getStoragePrefix() + "/**"));
        setAuthorities(authorities);
    }

    public abstract RoleEntity getRole();
}
