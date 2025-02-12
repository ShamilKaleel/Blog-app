package org.ruhuna.blogapp.security.service;

import org.ruhuna.blogapp.model.User;
import org.ruhuna.blogapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);

        User user = userOpt.orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email: " + username ));

        return  UserDetailsImpl.build(user);
    }
}
