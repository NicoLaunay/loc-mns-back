package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.security.AppUserDetails;
import com.mns.cda.loc_mns.service.IAppUserService;
import com.mns.cda.loc_mns.view.AppUserView;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    @Value("${jwt.secret}")
    protected String jwtSecret;

    private final IAppUserService userService;
    private final AuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/sign-in")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> signIn(@RequestBody @Valid AppUser newUser) {
        AppUser created = userService.create(newUser);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUser user) {
        log.info("Login appelé");
        log.info("Email reçu : {}", user.getEmail());
        try {
            AppUserDetails appUser = (AppUserDetails) authenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()))
                    .getPrincipal();

            String jwt = Jwts.builder()
                    .setSubject(user.getEmail())
                    .addClaims(Map.of("role", appUser.getUser().getRole().getName()))
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();

            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
