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
    @JsonView(AppUserView.class) // évite l'affichage du MdP
    public ResponseEntity<AppUser> signIn(@RequestBody @Valid AppUser newUser) {

        newUser.setPassword((passwordEncoder.encode(newUser.getPassword())));
        userService.createAppUser(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUser user) {

        try {
            AppUserDetails appUser = (AppUserDetails) authenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()))
                    .getPrincipal();

            // Construction du JWT
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
