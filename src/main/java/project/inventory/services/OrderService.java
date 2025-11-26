package project.inventory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import project.inventory.domain.order.Order;
import project.inventory.domain.order.OrderItem;
import project.inventory.domain.order.dto.CreateOrderDTO;
import project.inventory.domain.order.dto.OrderItemDTO;
import project.inventory.domain.order.enums.OrderStatus;
import project.inventory.repositories.OrderRepository;
import project.inventory.repositories.ProductRepository;
import project.inventory.repositories.UserRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Retry(name = "orderService")
    @Transactional
    public Order create(CreateOrderDTO order) {
        var user = userRepository.findById(order.clientId()).orElse(null);

        if (user == null) {
            return null;
        }

        Order orderEntity = new Order();

        orderEntity.setClient(user);
        orderEntity.setStatus(OrderStatus.PENDING);

        for (OrderItemDTO itemDTO : order.items()) {
            var product = productRepository.findById(itemDTO.productId());

            if (product.isEmpty()) {
                return null;
            }

            OrderItem orderItem = new OrderItem();

            orderItem.getId().setOrder(orderEntity);
            orderItem.getId().setProduct(product.get());
            orderItem.setQuantity(itemDTO.quantity());

            orderEntity.getItems().add(orderItem);
        }

        return orderRepository.save(orderEntity);
    }

    public void cancel(Order order) {
        orderRepository.delete(order);
    }

}