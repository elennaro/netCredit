package art.alex.services;

import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userLoaded;
        User client = usersRepository.findByUsername(username);
        userLoaded = new org.springframework.security.core.userdetails.User(
                client.getUsername(),
                client.getPassword(),
                new HashSet<GrantedAuthority>()
        );
        return userLoaded;
    }
}
