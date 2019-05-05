package ir.markazandroid.UniEngine.persistance.interfaces;


import ir.markazandroid.UniEngine.persistance.entity.UserEntity;

import java.util.List;

/**
 * Created by Ali on 30/01/2018.
 */
public interface UserDao {

    UserEntity getUserByUsername(String username, String... fields);

    UserEntity getUserByUserId(int userId, String... fields);

    UserEntity getUserByPhone(String phone, String... fields);

    void saveUser(UserEntity userEntity);

    void updateUser(UserEntity userEntity, String... fields);

    List<UserEntity> getUsers(int offset, int limit);

    long getUsersCount();

    void detachObject(Object o);

}
