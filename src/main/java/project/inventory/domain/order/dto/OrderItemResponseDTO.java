package project.inventory.domain.order.dto;

public record OrderItemResponseDTO(
    Long productId,
    String productName,
    Integer quantity
) {}