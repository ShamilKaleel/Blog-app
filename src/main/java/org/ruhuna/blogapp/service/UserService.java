package org.ruhuna.blogapp.service;

import org.ruhuna.blogapp.exceptions.ResourceNotFoundException;
import org.ruhuna.blogapp.model.User;
import org.ruhuna.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder( 12);

    @Autowired
    private AuthenticationManager authenticationManager;

   @Autowired
   private JWTService jwtService;

    public User registerUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new ResourceNotFoundException("Username already exists");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }



    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if(!authentication.isAuthenticated()){
            throw new ResourceNotFoundException("Invalid username or password");
        }

        return jwtService.generateToken(user.getUsername());
    }
}
