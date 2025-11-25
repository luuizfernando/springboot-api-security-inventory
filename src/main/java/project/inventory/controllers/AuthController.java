package project.inventory.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.inventory.domain.user.User;
import project.inventory.domain.user.dto.LoginRequestDTO;
import project.inventory.domain.user.dto.LoginResponseDTO;
import project.inventory.domain.user.dto.RegisterDTO;
import project.inventory.infra.security.TokenService;
import project.inventory.repositories.UserRepository;

@RestController
@RequestMapping("auth")
@Tag(name = "Auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica o usuário e retorna um JWT")
    @ApiResponse(responseCode = "200", description = "Token JWT gerado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class)))
    public ResponseEntity login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciais de login",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = LoginRequestDTO.class),
                examples = @ExampleObject(value = "{\\\"username\\\":\\\"admin\\\",\\\"password\\\":\\\"123\\\"}")))
        @Validated LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar", description = "Cria um novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário criado")
    public ResponseEntity register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados de registro",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RegisterDTO.class),
                examples = @ExampleObject(value = "{\\\"username\\\":\\\"user\\\",\\\"password\\\":\\\"123\\\",\\\"role\\\":\\\"USER\\\"}")))
        @Validated RegisterDTO data) {
        if (this.userRepository.findByUsername(data.username()) != null){
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), encryptedPassword, data.role());

        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

}
