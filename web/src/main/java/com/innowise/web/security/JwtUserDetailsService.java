package com.innowise.web.security;

import com.innowise.core.entity.role.Role;
import com.innowise.core.entity.user.User;
import com.innowise.web.feign.UserFeignClient;
import com.innowise.web.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    @Override
    public JwtUser loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userFeignClient.getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User with login \"" + login + "\" does not exist");
        } else  {
            return new JwtUser(user.getId(),
                    user.getLogin(),
                    user.getPassword(),
                    user.getClientId(),
                    true,
                    convertToSpringAuthorities(user.getRoles()));
        }
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Role> roles) {
        if (roles != null && roles.size() > 0){
            return roles.stream()
                    .map(role -> role.getRole().name())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }
}
