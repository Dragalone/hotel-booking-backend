package org.example.hotelbookingbackend.security;


import lombok.RequiredArgsConstructor;
import org.example.hotelbookingbackend.exception.EntityNotFoundException;
import org.example.hotelbookingbackend.repository.UserRepository;
import org.example.hotelbookingbackend.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AppUserPrincipal(userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format("User with with username {0} not found!", username)
                        )));
    }
}
