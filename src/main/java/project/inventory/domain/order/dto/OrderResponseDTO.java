package project.inventory.domain.order.dto;

import java.util.List;

import project.inventory.domain.order.enums.OrderStatus;

public record OrderResponseDTO(
    Long id,
    Long clientId,
    String clientName,
    OrderStatus status,
    List<OrderItemResponseDTO> items
) {}