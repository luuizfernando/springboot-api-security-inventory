package project.inventory.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import project.inventory.domain.order.Order;
import project.inventory.domain.order.dto.CreateOrderDTO;
import project.inventory.domain.order.enums.OrderStatus;
import project.inventory.services.OrderService;

@RestController
@RequestMapping("orders")
@Tag(name = "Orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Lista pedidos com filtros opcionais")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos",
        content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Order.class))))
    public ResponseEntity<List<Order>> findAll(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end
    ) {
        List<Order> orders = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID")
    @ApiResponse(responseCode = "200", description = "Pedido",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order order = service.findById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PostMapping
    @Operation(summary = "Criar pedido")
    @ApiResponse(responseCode = "201", description = "Pedido criado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    public ResponseEntity<Order> insert(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do pedido",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CreateOrderDTO.class),
                examples = @ExampleObject(value = "{\\\"clientId\\\":1,\\\"items\\\":[{\\\"productId\\\":1,\\\"quantity\\\":2}]}")))
        CreateOrderDTO orderDTO) {
        Order createdOrder = service.create(orderDTO);

        if (createdOrder == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pedido")
    @ApiResponse(responseCode = "204", description = "Pedido cancelado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Order order = service.findById(id);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        service.cancel(order);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
