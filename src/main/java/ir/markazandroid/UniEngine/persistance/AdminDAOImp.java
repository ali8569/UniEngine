package ir.markazandroid.UniEngine.persistance;

import ir.markazandroid.UniEngine.persistance.entity.AdminEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.AdminDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Created by Ali on 4/10/2019.
 */
@Repository
public class AdminDAOImp implements AdminDAO {

    @PersistenceContext
    public EntityManager entityManager;


    @Override
    public AdminEntity getAdminByUsername(String username) {
        try {
            return entityManager.createQuery("from AdminEntity where username=:username",AdminEntity.class)
                    .setParameter("username",username)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public void detach(Object o) {
        entityManager.detach(o);

    }
}
