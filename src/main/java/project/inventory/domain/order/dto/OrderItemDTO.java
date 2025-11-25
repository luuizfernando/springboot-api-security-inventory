package project.inventory.domain.order.dto;

public record OrderItemDTO(
    Long productId,
    Integer quantity
) {}