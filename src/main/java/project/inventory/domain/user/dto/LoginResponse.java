package project.inventory.domain.user.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
    
}