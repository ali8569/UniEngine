package ir.markazandroid.UniEngine.persistance.entity;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ali on 4/9/2019.
 */
@JSON
@Entity
@Table(name = "role", schema = "uni_engine")
public class RoleEntity implements Serializable {
    private int roleId;
    private String role;
    private String description;
    private String name;
    private List<PrivilegeEntity> privileges;


    public RoleEntity(String role, PrivilegeEntity... authorities) {
        this.role = role;
        this.privileges= Arrays.asList(authorities);
    }
    
    public RoleEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "role", nullable = false, length = 50)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JSON
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JSON
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }

    @ManyToMany
    @JoinTable(name = "role_has_privileges",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = { @JoinColumn(name = "privilege_id") })
    public List<PrivilegeEntity> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegeEntity> privileges) {
        this.privileges = privileges;
    }

    /**
     * Should be called within active session from managed bean object
     * @return
     */
    @Transient
    public ArrayList<GrantedAuthority> getAllAuthorities(){
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+getRole()));
        authorities.addAll(getPrivileges());
        return authorities;
    }
}
