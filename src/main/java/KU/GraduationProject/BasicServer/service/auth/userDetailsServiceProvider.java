package KU.GraduationProject.BasicServer.service.auth;

import KU.GraduationProject.BasicServer.service.kakaoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class userDetailsServiceProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // ..TODO DB 처리
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                authentication.getName(),
                null,
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
