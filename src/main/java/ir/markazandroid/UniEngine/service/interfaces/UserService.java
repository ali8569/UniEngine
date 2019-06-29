package ir.markazandroid.UniEngine.service.interfaces;

import ir.markazandroid.UniEngine.exception.VerificationCodeAlreadySentException;
import ir.markazandroid.UniEngine.exception.VerificationCodeIncorrectException;
import ir.markazandroid.UniEngine.exception.VerificationSmsExpiredException;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Locale;

/**
 * Created by Ali on 30/01/2018.
 */
public interface UserService extends UserDetailsService {
    void register(UserEntity userEntity);

    boolean sendVerificationCode(int userId, String address, Locale locale, boolean sms) throws VerificationCodeAlreadySentException;

    boolean checkVerificationCode(int userId);

    void verifyUser(String code, UserEntity userEntity) throws VerificationCodeIncorrectException,VerificationSmsExpiredException;

    boolean sendPasswordResetSms(String phone, String username);

    boolean checkPasswordResetSms(String username);

    void verifyPasswordResetToken(int userId, String token);

    void changeUserPassword(int userId, String password);

    UserEntity getUserByUserId(int userId);

    List<UserEntity> getUsers(int offset, int limit);

    long getUsersCount();

    void changeUserEmail(UserEntity userEntity, String email);

    void changeUserPhone(UserEntity userEntity, String phone);

    void changeUserPhone2(UserEntity userEntity, String phone2);

    /**
     * firstname lastname
     * @param userEntity
     */
    void changeUserDetails(UserEntity userEntity);

    void initUserParams(UserEntity userEntity);

    List<DeviceEntity> getUserOnlineDevices(long userId);

}
