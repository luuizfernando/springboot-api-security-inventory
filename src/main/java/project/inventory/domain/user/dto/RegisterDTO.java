package project.inventory.domain.user.dto;

import project.inventory.domain.user.enums.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
    
}