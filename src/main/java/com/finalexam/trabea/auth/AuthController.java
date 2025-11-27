package com.finalexam.trabea.auth;

import com.finalexam.trabea.auth.dto.RequestLogin;
import com.finalexam.trabea.auth.dto.ResponseLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<ResponseLogin> login(@Valid @RequestBody RequestLogin requestLogin){
        return ResponseEntity.ok(authService.login(requestLogin));
    }
}
