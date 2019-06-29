package ir.markazandroid.UniEngine.security.device;

import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.service.interfaces.DeviceService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by Ali on 5/28/2018.
 */
@Component
public class UUIDAuthenticationProvider implements AuthenticationProvider {

    private final DeviceService deviceService;

    public UUIDAuthenticationProvider(DeviceService userService) {
        this.deviceService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DeviceEntity deviceEntity = deviceService.getDeviceByUUID(authentication.getPrincipal().toString());
        if (deviceEntity == null) throw new UsernameNotFoundException(authentication.getPrincipal().toString());
        return new UUIDAuthentication(deviceEntity, authentication.getCredentials(), deviceEntity.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UUIDAuthentication.class.equals(authentication);
    }
}
