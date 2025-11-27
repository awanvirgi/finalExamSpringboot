package com.finalexam.trabea.auth;

import com.finalexam.trabea.user.User;
import com.finalexam.trabea.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         User user = userRepository.findById(email).orElseThrow(
                ()->  new UsernameNotFoundException("No User with Work Email : "+email)
        );
        return new AuthUserDetails(user);
    }
}
