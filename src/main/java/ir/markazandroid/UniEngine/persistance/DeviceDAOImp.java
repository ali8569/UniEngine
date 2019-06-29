package ir.markazandroid.UniEngine.persistance;

import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.DeviceDAO;
import ir.markazandroid.UniEngine.util.DaoUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Ali on 6/10/2019.
 */
@Repository
public class DeviceDAOImp implements DeviceDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveDevice(DeviceEntity deviceEntity) {
        entityManager.persist(deviceEntity);
    }

    @Override
    public void updateDevice(DeviceEntity deviceEntity, String... fields) {
        if (fields.length > 0) {
            CriteriaUpdate<DeviceEntity> criteriaUpdate = entityManager.getCriteriaBuilder()
                    .createCriteriaUpdate(DeviceEntity.class);
            Root<DeviceEntity> root = criteriaUpdate.from(DeviceEntity.class);
            DaoUtils.setUpdateFields(deviceEntity, criteriaUpdate, fields);
            criteriaUpdate.where(entityManager.getCriteriaBuilder()
                    .equal(root.get("deviceId"), deviceEntity.getDeviceId()));

            entityManager.createQuery(criteriaUpdate)
                    .executeUpdate();
        } else {
            Session session = entityManager.unwrap(Session.class);
            session.update(deviceEntity);
        }
    }

    @Override
    public DeviceEntity getDeviceById(long deviceId, String... fields) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DeviceEntity> criteriaQuery = criteriaBuilder.createQuery(DeviceEntity.class);
        Root<DeviceEntity> root = criteriaQuery.from(DeviceEntity.class);

        if (fields.length > 0)
            criteriaQuery.multiselect(DaoUtils.getSelectionsByFieldNames(root, fields));

        criteriaQuery.where(criteriaBuilder.equal(root.get("deviceId"), deviceId));

        try {
            return entityManager.createQuery(criteriaQuery)
                    .getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        }

    }

    @Override
    public DeviceEntity getDeviceByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DeviceEntity> criteriaQuery = criteriaBuilder.createQuery(DeviceEntity.class);
        Root<DeviceEntity> root = criteriaQuery.from(DeviceEntity.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));

        try {
            return entityManager.createQuery(criteriaQuery)
                    .getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        }
    }

    @Override
    public DeviceEntity getDeviceByUUID(String UUID) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DeviceEntity> criteriaQuery = criteriaBuilder.createQuery(DeviceEntity.class);
        Root<DeviceEntity> root = criteriaQuery.from(DeviceEntity.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("uuid"), UUID));

        try {
            return entityManager.createQuery(criteriaQuery)
                    .getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        }
    }

    @Override
    public List<DeviceEntity> getUserDevices(long userId) {
        return entityManager.createQuery("from DeviceEntity where userId=:userId", DeviceEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
