package com.innowise.web.security;

import com.innowise.web.dto.user.request.GetUserByLoginRequest;
import com.innowise.web.dto.user.response.GetUserResponse;
import com.innowise.web.enums.Roles;
import com.innowise.web.feign.UserFeignClient;
import com.innowise.web.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        GetUserResponse user = userFeignClient.getUserByLogin(new GetUserByLoginRequest(login));
        if (user == null) {
            throw new UsernameNotFoundException("User with login \"" + login + "\" does not exist");
        } else  {
            return new JwtUser(user.getId(),
                    user.getLogin(),
                    user.getPassword(),
                    user.getClientId(),
                    true,
                    convertUserRolesToSpringAuthorities(user.getUserRoles()));
        }
    }

    private Collection<GrantedAuthority> convertUserRolesToSpringAuthorities(Set<Roles> roles) {
        if (roles != null && roles.size() > 0) {
            return roles.stream()
                    .map(Roles::name)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
