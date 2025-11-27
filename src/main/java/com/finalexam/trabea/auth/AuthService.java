package com.finalexam.trabea.auth;

import com.finalexam.trabea.auth.dto.RequestGenerateToken;
import com.finalexam.trabea.auth.dto.RequestLogin;
import com.finalexam.trabea.auth.dto.ResponseLogin;
import com.finalexam.trabea.auth.jwt.JwtService;
import com.finalexam.trabea.role.Role;
import com.finalexam.trabea.role.RolesName;
import com.finalexam.trabea.user.User;
import com.finalexam.trabea.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ResponseLogin login(RequestLogin request){
        User user = userRepository.findById(request.getEmail()).orElseThrow(
                ()-> new UsernameNotFoundException("Account not found or username and password are incorrect")
        );
        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new BadCredentialsException("Account not found or username and password are incorrect");
        }

        RolesName thisRole = null;
        for (Role role : user.getRoles()) {
            if (role.getName().equals(request.getRolesName())){
                thisRole = request.getRolesName();
                break;
            }
        }
        if (thisRole == null){
            throw new AccessDeniedException("This account does not have the required role");
        }
        String token = jwtService.generateToken(new RequestGenerateToken(request.getEmail(),thisRole));
        return new ResponseLogin(token);
    }
}
