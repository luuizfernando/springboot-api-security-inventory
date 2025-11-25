package project.inventory.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.inventory.domain.user.User;
import project.inventory.services.UserService;

@RestController
@RequestMapping("users")
@Tag(name = "Users")
public class UserController {
    
    @Autowired
    UserService service;

    @GetMapping
    @Operation(summary = "Listar usuários")
    @ApiResponse(responseCode = "200", description = "Lista de usuários",
        content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = User.class))))
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

}
