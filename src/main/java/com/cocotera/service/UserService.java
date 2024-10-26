package com.cocotera.service;


import com.cocotera.interfaces.IUserRepository;
import com.cocotera.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        switch (user.getRole()) {
            case 0:
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            case 1:
                authorities.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
                break;
            case 2:
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + user.getRole());
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


}
