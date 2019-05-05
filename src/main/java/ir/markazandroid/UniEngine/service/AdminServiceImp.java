package ir.markazandroid.UniEngine.service;

import ir.markazandroid.UniEngine.persistance.entity.AdminEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.AdminDAO;
import ir.markazandroid.UniEngine.service.interfaces.AdminService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;

/**
 * Created by Ali on 4/10/2019.
 */
@Service
public class AdminServiceImp implements AdminService {

    private final AdminDAO adminDAO;
    private final BCryptPasswordEncoder passwordEncoder;


    public AdminServiceImp(AdminDAO adminDAO, BCryptPasswordEncoder passwordEncoder) {
        this.adminDAO = adminDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) throw new UsernameNotFoundException("Empty username");
        AdminEntity adminEntity = adminDAO.getAdminByUsername(username);
        if (adminEntity == null) throw new UsernameNotFoundException("admin \"" + username + "\" not found.");
        adminDAO.detach(adminEntity);
        adminEntity.makePrivileges();
        adminEntity.setPassword(passwordEncoder.encode(adminEntity.getPassword()));
        return adminEntity;
    }
}
