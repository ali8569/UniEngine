package ir.markazandroid.UniEngine.service;

import ir.markazandroid.UniEngine.UniEngineApplication;
import ir.markazandroid.UniEngine.exception.*;
import ir.markazandroid.UniEngine.object.Token;
import ir.markazandroid.UniEngine.persistance.entity.PrivilegeEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.TokenRepository;
import ir.markazandroid.UniEngine.persistance.interfaces.UserDao;
import ir.markazandroid.UniEngine.service.interfaces.UserService;
import ir.markazandroid.UniEngine.sms.KavenegarApi;
import ir.markazandroid.UniEngine.util.EmailUtils;
import ir.markazandroid.UniEngine.util.Utils;
import netscape.security.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Ali on 30/01/2018.
 */
@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final TokenRepository tokenRepository;
    private final Random random;
    private final KavenegarApi kavenegarApi;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailUtils emailUtils;
    private final MessageSource messageSource;


    @Autowired
    public UserServiceImp(UserDao userDao, TokenRepository tokenRepository, Random random, KavenegarApi kavenegarApi, BCryptPasswordEncoder passwordEncoder, EmailUtils emailUtils, MessageSource messageSource) {
        this.userDao = userDao;
        this.tokenRepository = tokenRepository;
        this.random = random;
        this.kavenegarApi = kavenegarApi;
        this.passwordEncoder = passwordEncoder;
        this.emailUtils = emailUtils;
        this.messageSource = messageSource;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) throw new UsernameNotFoundException("Empty username");
        UserEntity userEntity = userDao.getUserByUsername(username);
        if (userEntity == null) throw new UsernameNotFoundException("user \""+username+"\" not found.");
        userDao.detachObject(userEntity);
        userEntity.makePrivileges();
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntity;
    }

    @Transactional
    @Override
    public void register(UserEntity userEntity) {
        userEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userEntity.setStatus(UserEntity.STATUS_NOT_VERIFIED);
        userEntity.setRoleId(1);
        userDao.saveUser(userEntity);
    }

    @Override
    public boolean sendVerificationCode(int userId, String address,Locale locale,boolean sms) throws VerificationCodeAlreadySentException {
        Token token=tokenRepository.getTokenByUsername("user_"+userId);
        if (token!=null && token.getTime()+60000>System.currentTimeMillis())
            throw new VerificationCodeAlreadySentException((60000-(System.currentTimeMillis()-token.getTime()))/1000);

        String ts = (random.nextInt(899999) + 100000) + "";
        token=new Token(ts,System.currentTimeMillis());
        boolean sent=false;
        String message = messageSource.getMessage("account_verification_code",null,"Verification Code: ",locale)+ts;
        String title = messageSource.getMessage("account_verification",null,"Account Verification ",locale);
        try {
            if (sms)
                sent = kavenegarApi.send("10000004044404",address,message).getStatus()==1;
            else
                sent=emailUtils.sendSimpleMessage(address,title,message);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (sent) tokenRepository.add("user_"+userId,token);
        return sent;
    }

    @Override
    public boolean checkVerificationCode(int userId) {
        Token token=tokenRepository.getTokenByUsername("user_"+userId);
        if (token!=null && token.getTime()+60000>System.currentTimeMillis())
            throw new VerificationCodeAlreadySentException((60000-(System.currentTimeMillis()-token.getTime()))/1000);

        return token != null;
    }

    @Transactional
    @Override
    public void verifyUser(String code, UserEntity userEntity) throws VerificationCodeIncorrectException, VerificationSmsExpiredException {
        Token token=tokenRepository.getTokenByUsername("user_"+userEntity.getUserId());

        if (token==null || !token.getToken().equals(code))
            throw new VerificationCodeIncorrectException();
        if (token.getTime()>=System.currentTimeMillis()+20*60*1000)
            throw new VerificationSmsExpiredException();


        userEntity.setStatus(UserEntity.STATUS_ACTIVE);
        userDao.updateUser(userEntity,"status");
        tokenRepository.remove("user_"+userEntity.getUserId());
    }

    @Override
    public boolean sendPasswordResetSms(String phone,String username) {

        UserEntity userEntity = userDao.getUserByUsername(username,"userId","phone","username");
        if (userEntity==null || !userEntity.getPhone().equals(phone)) throw new UsernameNotFoundException("user not found");

        Token token=tokenRepository.getTokenByUsername("user_password_reset_"+userEntity.getUserId());
        if (token!=null && token.getTime()+60*1000>System.currentTimeMillis())
            throw new ResetPasswordSmsAlreadySentException((60*1000-(System.currentTimeMillis()-token.getTime()))/1000);

        String ts = Utils.getSaltString(random,10);
        token=new Token(ts,System.currentTimeMillis());
        boolean sent=false;
        try {
            sent = kavenegarApi.send("10000004044404",phone,"برای تغییر گذرواژه روی لینک زیر کلیک کنید.\n"
                    +"http://89.42.210.32/UniEnginev2/web/user/authentication/vrpt/"+userEntity.getUserId()+"/"+ts).getStatus()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        if (sent) tokenRepository.add("user_password_reset_"+userEntity.getUserId(),token);
        return sent;
    }

    @Override
    public boolean checkPasswordResetSms(String username) {
       // Token token=tokenRepository.getTokenByUsername("user_password_reset_"+username);
       // if (token!=null && token.getTime()+60*1000>System.currentTimeMillis())
       //     throw new ResetPasswordSmsAlreadySentException((60*1000-(System.currentTimeMillis()-token.getTime()))/1000);

      //  return token != null;
        return true;
    }

    @Override
    public void verifyPasswordResetToken(int userId, String tokenString) {
        Token token=tokenRepository.getTokenByUsername("user_password_reset_"+userId);

        if (token==null || !token.getToken().equals(tokenString))
            throw new ResetPasswordIncorrectException();
        if (token.getTime()>=System.currentTimeMillis()+20*60*1000)
            throw new ResetPasswordSmsExpiredException();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userEntity , null, Collections.singletonList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        tokenRepository.remove("user_password_reset_"+userId);
    }

    @Transactional
    @Override
    public void changeUserPassword(int userId, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setPassword(password);
        userDao.updateUser(userEntity,"password");
    }

    @Override
    public UserEntity getUserByUserId(int userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    public List<UserEntity> getUsers(int offset, int limit) {
        return userDao.getUsers(offset, limit);
    }

    @Override
    public long getUsersCount() {
        return userDao.getUsersCount();
    }

    @Transactional
    @Override
    public void changeUserEmail(UserEntity userEntity, String email) {
        UserEntity user = new UserEntity();
        user.setUserId(userEntity.getUserId());
        user.setEmail(email);
        user.setStatus(UserEntity.STATUS_NOT_VERIFIED);
        userDao.updateUser(user,"email","status");
        userEntity.setEmail(email);
        userEntity.setStatus(UserEntity.STATUS_NOT_VERIFIED);
    }

    @Transactional
    @Override
    public void changeUserPhone(UserEntity userEntity, String phone) {
        UserEntity user = new UserEntity();
        user.setUserId(userEntity.getUserId());
        user.setPhone(phone);
        user.setStatus(UserEntity.STATUS_NOT_VERIFIED);
        userDao.updateUser(user,"phone","status");
        userEntity.setPhone(phone);
        userEntity.setStatus(UserEntity.STATUS_NOT_VERIFIED);
    }

    @Transactional
    @Override
    public void changeUserPhone2(UserEntity user, String phone2) {
        user.setPhone2(phone2);
        userDao.updateUser(user,"phone2");
    }

    @Transactional
    @Override
    public void changeUserDetails(UserEntity userEntity) {
        userDao.updateUser(userEntity,"firstName","lastName");
    }


}
