package project.inventory.domain.order.dto;

import java.util.List;

public record CreateOrderDTO(
    Long clientId,
    List<OrderItemDTO> items
) {}