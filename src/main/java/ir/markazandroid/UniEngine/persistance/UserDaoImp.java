package ir.markazandroid.UniEngine.persistance;

import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.UserDao;
import ir.markazandroid.UniEngine.util.DaoUtils;
import org.hibernate.Session;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Ali on 30/01/2018.
 */
@Repository
public class UserDaoImp implements UserDao {

    public static final String CACHE_NAME_COUNTS = "counts";
    public static final String CACHE_KEY_USERS_COUNT="key_user_counts";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserEntity getUserByUsername(String username, String... fields) {
        try {
            if (fields.length>0){
                CriteriaBuilder criteriaBuilder =entityManager.getCriteriaBuilder();
                CriteriaQuery<UserEntity> criteriaQuery=criteriaBuilder.createQuery(UserEntity.class);
                Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

                criteriaQuery.multiselect(DaoUtils.getSelectionsByFieldNames(root,fields))
                        .where(criteriaBuilder.equal(root.get("username"),username));

                return entityManager.createQuery(criteriaQuery)
                        .getSingleResult();
            }
            return (UserEntity) entityManager
                    .createQuery("from UserEntity where username=:username")
                    .setParameter("username",username)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public UserEntity getUserByUserId(int userId, String... fields) {
        try {
            if (fields.length>0){
                CriteriaBuilder criteriaBuilder =entityManager.getCriteriaBuilder();
                CriteriaQuery<UserEntity> criteriaQuery=criteriaBuilder.createQuery(UserEntity.class);
                Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

                criteriaQuery.multiselect(DaoUtils.getSelectionsByFieldNames(root,fields))
                        .where(criteriaBuilder.equal(root.get("userId"),userId));

                return entityManager.createQuery(criteriaQuery)
                        .getSingleResult();
            }
            return (UserEntity) entityManager
                    .createQuery("from UserEntity where userId=:userId")
                    .setParameter("userId",userId)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public UserEntity getUserByPhone(String phone, String... fields) {
        try {
            if (fields.length>0){
                CriteriaBuilder criteriaBuilder =entityManager.getCriteriaBuilder();
                CriteriaQuery<UserEntity> criteriaQuery=criteriaBuilder.createQuery(UserEntity.class);
                Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

                criteriaQuery.multiselect(DaoUtils.getSelectionsByFieldNames(root,fields))
                        .where(criteriaBuilder.equal(root.get("phone"),phone));

                return entityManager.createQuery(criteriaQuery)
                        .getSingleResult();
            }
            return (UserEntity) entityManager
                    .createQuery("from UserEntity where phone=:phone")
                    .setParameter("phone",phone)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @CacheEvict(cacheNames = CACHE_NAME_COUNTS,key = "T(ir.markazandroid.UniEngine.persistance.UserDaoImp).CACHE_KEY_USERS_COUNT")
    @Override
    public void saveUser(UserEntity userEntity) {
       entityManager.persist(userEntity);
    }

    @Override
    public void updateUser(UserEntity userEntity, String... fields) {
        if (fields.length>0) {
            CriteriaUpdate<UserEntity> criteriaUpdate = entityManager.getCriteriaBuilder()
                    .createCriteriaUpdate(UserEntity.class);
            Root<UserEntity> root = criteriaUpdate.from(UserEntity.class);
            DaoUtils.setUpdateFields(userEntity,criteriaUpdate,fields);
            criteriaUpdate.where(entityManager.getCriteriaBuilder()
                    .equal(root.get("userId"), userEntity.getUserId()));

            entityManager.createQuery(criteriaUpdate)
                    .executeUpdate();
        } else {
            Session session = entityManager.unwrap(Session.class);
            session.update(userEntity);
        }
    }

    @Override
    public List<UserEntity> getUsers(int offset, int limit) {
        return entityManager.createQuery("from UserEntity order by createTime desc ",UserEntity.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


    @Cacheable(cacheNames = CACHE_NAME_COUNTS,key = "T(ir.markazandroid.UniEngine.persistance.UserDaoImp).CACHE_KEY_USERS_COUNT")
    @Override
    public long getUsersCount() {
        return ((BigInteger)entityManager.createNativeQuery("SELECT count(*) FROM UniEngine.user")
                .getSingleResult()).longValueExact();
    }

    @Override
    public void detachObject(Object o) {
        entityManager.detach(o);
    }
}
