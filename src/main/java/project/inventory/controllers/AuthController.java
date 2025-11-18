package project.inventory.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import project.inventory.domain.user.dto.LoginRequest;
import project.inventory.domain.user.dto.LoginResponse;
import project.inventory.services.UserService;

@RestController
public class AuthController {

    @Autowired
    JwtEncoder jwtEncoder;

    @Autowired
    UserService service;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var user = service.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Username or Password not valid");
        }

        var expiresIn = 300L;
        var now = Instant.now();

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getUsername())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        
        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

}