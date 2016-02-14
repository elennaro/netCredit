package art.alex.services;

import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Deals correctly with authentication principal
 */
@Service
public class CustomUserDetailsService implements AdvancedUserDetailService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User client = usersRepository.findByUsername(username);
        if(client == null) {
            throw new UsernameNotFoundException("Could not find user " + username);
        }
        return new UserRepositoryUserDetails(client);
    }

    public boolean autoLogin(User user, HttpServletRequest request) {
        try {
            // generate session if one doesn't exist
            request.getSession();
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, getDefaultAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            logger.error("Error loging registered user in", e);
            return false;
        }
        return true;
    }

    private static Collection<? extends GrantedAuthority> getDefaultAuthorities(){
        return Stream.generate(() -> new SimpleGrantedAuthority("ROLE_USER")).limit(1).collect(Collectors.toSet());
    }

    private final static class UserRepositoryUserDetails extends User implements UserDetails {

        @Override public Collection<? extends GrantedAuthority> getAuthorities() {
            return CustomUserDetailsService.getDefaultAuthorities();
        }

        private UserRepositoryUserDetails(User user) {
            super(user);
        }

        @Override public boolean isAccountNonExpired() {
            return true;
        }

        @Override public boolean isAccountNonLocked() {
            return true;
        }

        @Override public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override public boolean isEnabled() {
            return true;
        }
    }
}
