package com.example.demo.controller;

import com.example.demo.dto.RegistrationDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;
    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * sign in
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody @Valid UserDto userDTO) {
        String jwt = userService.signIn(userDTO).getAccessToken();
        return ResponseEntity.ok(jwt);
    }

    /**
     * sign up
     *
     * @param registrationDTO
     * @return
     */
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid RegistrationDto registrationDTO) {
        userService.createUser(registrationDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * log out
     *
     * @param token
     * @return
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            tokenService.logout(token.substring(7));
        }
        return ResponseEntity.ok().build();
    }
}
