package ir.markazandroid.UniEngine.persistance.interfaces;

import ir.markazandroid.UniEngine.persistance.entity.AdminEntity;

/**
 * Created by Ali on 4/10/2019.
 */
public interface AdminDAO {

    AdminEntity getAdminByUsername(String username);

    void detach(Object o);
}
