package com.siemens.sl.apigateway.security;

import com.siemens.sl.apigateway.controller.AuthenticationController;
import com.siemens.sl.apigateway.model.Role;
import com.siemens.sl.apigateway.model.User;
import com.siemens.sl.apigateway.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private static Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUser(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (user == null) {
            logger.error("User with name: " + username + " not found, throwing UsernameNotFoundException");
            throw new UsernameNotFoundException("Invalid user");
        }

        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
}
